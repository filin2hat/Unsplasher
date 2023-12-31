package com.biryulindevelop.unsplash.presentation.screens.photos.list

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.domain.model.Photo
import com.biryulindevelop.domain.state.ClickableView
import com.biryulindevelop.domain.state.LoadState
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.FragmentPhotosBinding
import com.biryulindevelop.unsplash.presentation.screens.photos.list.adapter.PhotoPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos) {

    private val binding by viewBinding(FragmentPhotosBinding::bind)
    private val viewModel: PhotosViewModel by viewModels()
    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClickItem(buttonState, item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFlowPhotos()
        getLoadingState()
        loadStateLike()
        settingAdapter()
        setSearchView()
        refresher()
    }

    private fun getFlowPhotos() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun onClickItem(buttonState: ClickableView, item: Photo) {
        val action =
            PhotosFragmentDirections.actionNavigationPhotosToNavigationPhotoDetails(item.id)
        when (buttonState) {
            ClickableView.PHOTO -> findNavController().navigate(action)
            ClickableView.LIKE -> viewModel.like(item)
        }
    }

    private fun setSearchView() {
        val searchView = binding.searchBarView.menu.getItem(0).actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setQuery(query) { adapter.refresh() }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun settingAdapter() {
        with(binding) {
            photoRecyclerView.adapter = adapter
            photoRecyclerView.itemAnimator?.changeDuration = 0
        }
    }

    private fun getLoadingState() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                with(binding) {
                    errorView.isVisible =
                        loadState.mediator?.refresh is androidx.paging.LoadState.Error
                    recyclerProgressBarView.isVisible =
                        loadState.mediator?.refresh is androidx.paging.LoadState.Loading
                }
            }
        }
    }

    private fun loadStateLike() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadStateLike ->
                binding.errorView.isVisible = loadStateLike == LoadState.ERROR
            }
        }
    }

    private fun refresher() {
        binding.swipeRefresh.setOnRefreshListener {
            with(binding) {
                photoRecyclerView.isVisible = true
                adapter.refresh()
                swipeRefresh.isRefreshing = false
            }
        }
    }
}
