package com.isaacsufyan.catchu.presentation.fragments.lab.sensor_setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentSensorSetupBinding

class SensorSetupFragment : BaseFragment() {

    private var _binding: FragmentSensorSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSensorSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOf(
            binding.photodiodeRow.vPhotodiode1,
            binding.photodiodeRow.vPhotodiode2,
            binding.photodiodeRow.vPhotodiode3,
            binding.photodiodeRow.vPhotodiode4
        ).forEach { diode -> diode.visibility = View.VISIBLE }

        binding.btnNext.setOnClickListener {
            val action = SensorSetupFragmentDirections.actionSensorSetupToPhotodiodeSync()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
