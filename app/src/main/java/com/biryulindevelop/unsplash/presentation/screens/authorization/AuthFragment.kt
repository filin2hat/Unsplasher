package com.biryulindevelop.unsplash.presentation.screens.authorization

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.biryulindevelop.domain.state.LoadState
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.databinding.FragmentAuthBinding
import com.biryulindevelop.unsplash.presentation.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding by viewBinding(FragmentAuthBinding::bind)
    private val viewModel: AuthViewModel by viewModels()
    private val args: AuthFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAuth()
        tokenObserve(
            SharedPreferencesUtils.createSharedPrefs(
                requireContext(),
                com.biryulindevelop.common.TOKEN_NAME
            )
        )
        loadingObserve()
        viewModel.createToken(args.code)
    }

    private fun startAuth() {
        binding.btnAuth.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(com.biryulindevelop.common.REQUEST))
            startActivity(intent)
        }
    }

    private fun tokenObserve(preferences: SharedPreferences) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.token.collect { token ->
                preferences.edit().putString(com.biryulindevelop.common.TOKEN_KEY, token).apply()
                preferences.edit().putBoolean(com.biryulindevelop.common.TOKEN_ENABLED, true)
                    .apply()
            }
        }
    }

    private fun loadingObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadState ->
                when (loadState) {
                    LoadState.START ->
                        setLoadState(
                            buttonIsEnabled = true,
                            progressIsVisible = false
                        )

                    LoadState.LOADING -> setLoadState(
                        buttonIsEnabled = false,
                        progressIsVisible = true
                    )

                    LoadState.SUCCESS -> {
                        setLoadState(
                            buttonIsEnabled = false,
                            progressIsVisible = false
                        )
                        findNavController().navigate(R.id.action_authFragment_to_navigation_photos)
                    }

                    LoadState.ERROR -> {
                        setLoadState(
                            buttonIsEnabled = true,
                            progressIsVisible = false
                        )
                        Toast.makeText(requireContext(), loadState.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun setLoadState(
        buttonIsEnabled: Boolean,
        progressIsVisible: Boolean
    ) {
        with(binding) {
            btnAuth.isEnabled = buttonIsEnabled
            progressBar.isVisible = progressIsVisible
        }
    }
}
