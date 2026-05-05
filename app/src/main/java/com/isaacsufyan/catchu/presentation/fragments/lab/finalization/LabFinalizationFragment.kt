package com.isaacsufyan.catchu.presentation.fragments.lab.finalization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentLabFinalizationBinding

class LabFinalizationFragment : BaseFragment() {

    private var _binding: FragmentLabFinalizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLabFinalizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvStudySiteValue.text    = rawMeasurements.studySite      ?: "-"
        binding.tvStudyIdValue.text      = rawMeasurements.studyId        ?: "-"
        binding.tvWaveValue.text         = rawMeasurements.labWave        ?: "-"
        binding.tvFirstNameValue.text    = rawMeasurements.labFirstName   ?: "-"
        binding.tvLastNameValue.text     = rawMeasurements.labLastName    ?: "-"
        binding.tvDateOfBirthValue.text  = rawMeasurements.labDateOfBirth ?: "-"
        binding.tvGenderValue.text       = rawMeasurements.selectedGender ?: "-"

        binding.btnNewParticipant.setOnClickListener {
            val options = NavOptions.Builder()
                .setPopUpTo(R.id.sensorSetupFragment, false)
                .build()
            findNavController().navigate(R.id.labLoginFragment, null, options)
        }

        binding.btnFinish.setOnClickListener {
            mainActivity.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
