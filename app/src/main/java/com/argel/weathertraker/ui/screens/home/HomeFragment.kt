package com.argel.weathertraker.ui.screens.home

import android.view.View
import com.argel.weathertraker.R
import com.argel.weathertraker.core.presentation.BaseFragment
import com.argel.weathertraker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.DelicateCoroutinesApi

@AndroidEntryPoint
@WithFragmentBindings
@DelicateCoroutinesApi
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun setBinding(view: View) {
        binding = FragmentHomeBinding.bind(view)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }
}