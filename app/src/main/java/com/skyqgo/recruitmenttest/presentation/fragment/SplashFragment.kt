package com.skyqgo.recruitmenttest.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.skyqgo.recruitmenttest.R
import com.skyqgo.recruitmenttest.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSplashAnimation()
    }

    @Suppress("DEPRECATION")
    private fun showSplashAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.side_slide)
        binding.companyLogo.startAnimation(slideAnimation)

        Handler().postDelayed(
            {
                findNavController()
                    .navigate(
                        SplashFragmentDirections
                            .actionSplashFragmentToMoviesFragment()
                    )
            }, 3000
        )
    }
}