package com.biryulindevelop.unsplash.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.findNavController
import com.biryulindevelop.unsplash.R
import com.biryulindevelop.unsplash.application.ONBOARDING_IS_SHOWN
import com.biryulindevelop.unsplash.application.TOKEN_NAME
import com.biryulindevelop.unsplash.databinding.FragmentOnboardingBinding
import com.biryulindevelop.unsplash.tools.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    private var mediator: TabLayoutMediator? = null

    override fun initBinding(inflater: LayoutInflater) = FragmentOnboardingBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setTabs()
        setAuthorizeButton()
        saveOnbordingShown()
    }

    private fun setViewPager() {
        binding.viewPager.adapter =
            ViewPagerAdapter(resources.getStringArray(R.array.onboarding_texts_array))
        binding.viewPager.registerOnPageChangeCallback(AnimateImageOnPageChange(binding.ellipseImage))
    }

    private fun setTabs() {
        mediator = TabLayoutMediator(binding.tabsView, binding.viewPager) { _, _ -> }
        mediator!!.attach()
    }

    private fun setAuthorizeButton() {
        binding.authorizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_onboarding_to_navigation_auth)
        }
    }

    private fun saveOnbordingShown() {
        val prefs = createSharedPreference(TOKEN_NAME)
        prefs.edit().putBoolean(ONBOARDING_IS_SHOWN, true).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
    }
}