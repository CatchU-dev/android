package com.isaacsufyan.catchu.presentation.fragments.lab.photodiode_sync

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentPhotodiodeSyncBinding

class PhotodiodeSyncFragment : BaseFragment() {

    private var _binding: FragmentPhotodiodeSyncBinding? = null
    private val binding get() = _binding!!

    private val FLASH_MS = 100L
    private val WAIT_MS  = 500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotodiodeSyncBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRepeat.setOnClickListener { runSequence() }
        binding.btnNext.setOnClickListener {
            val action = PhotodiodeSyncFragmentDirections.actionPhotodiodeSyncToLabDetails()
            findNavController().navigate(action)
        }
        // Delay auto-start past the 400ms enter animation so diode 1 is never clipped.
        runSequence(initialDelayMs = 450L)
    }

    private fun runSequence(initialDelayMs: Long = 0L) {
        binding.btnRepeat.visibility = View.INVISIBLE
        binding.btnNext.visibility   = View.INVISIBLE

        val diodes = listOf(
            binding.photodiodeRow.vPhotodiode1,
            binding.photodiodeRow.vPhotodiode2,
            binding.photodiodeRow.vPhotodiode3,
            binding.photodiodeRow.vPhotodiode4
        )
        diodes.forEach { it.visibility = View.INVISIBLE }

        val handler = Handler(Looper.getMainLooper())
        diodes.forEachIndexed { i, diode ->
            val onAt  = initialDelayMs + i * (FLASH_MS + WAIT_MS)
            val offAt = onAt + FLASH_MS
            handler.postDelayed({ diode.visibility = View.VISIBLE   }, onAt)
            handler.postDelayed({ diode.visibility = View.INVISIBLE }, offAt)
        }
        // Show buttons after last diode turns off
        val showButtonsAt = initialDelayMs + (diodes.size - 1) * (FLASH_MS + WAIT_MS) + FLASH_MS
        handler.postDelayed({
            binding.btnRepeat.visibility = View.VISIBLE
            binding.btnNext.visibility   = View.VISIBLE
        }, showButtonsAt)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
