package com.isaacsufyan.catchu.presentation.fragments.launch

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.databinding.FragmentLaunchBinding

class LaunchFragment : Fragment() {

    private var _binding: FragmentLaunchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ObjectAnimator.ofFloat(binding.viewLeft, "translationX", 100f).apply {
            duration = 500
            repeatCount = 3
            repeatMode = ObjectAnimator.REVERSE
            start()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    val action = LaunchFragmentDirections.actionLaunchToLanguage()
                    findNavController().navigate(action)
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {
                }
            })
        }

        ObjectAnimator.ofFloat(binding.viewRight, "translationX", -100f).apply {
            duration = 500
            repeatCount = 3
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}