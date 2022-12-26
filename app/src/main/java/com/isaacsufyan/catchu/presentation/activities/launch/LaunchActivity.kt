package com.isaacsufyan.catchu.presentation.activities.launch

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import com.isaacsufyan.catchu.base.BaseActivity
import com.isaacsufyan.catchu.databinding.ActivityLaunchBinding
import com.isaacsufyan.catchu.presentation.activities.main.MainActivity

class LaunchActivity : BaseActivity() {

    private var _binding: ActivityLaunchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ObjectAnimator.ofFloat(binding.viewLeft, "translationX", 100f).apply {
            duration = 500
            repeatCount = 3
            repeatMode = ObjectAnimator.REVERSE
            start()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
                    finish()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}