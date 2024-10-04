package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentDemoGraphicAndMedicalHistoryBinding
import com.isaacsufyan.catchu.utils.hideSoftKeyboard
import com.isaacsufyan.catchu.utils.showSnackBar
import com.isaacsufyan.catchu.utils.textToString
import java.text.SimpleDateFormat
import java.util.*


class DemoGraphicAndMedicalHistoryFragment : BaseFragment() {

    private var _binding: FragmentDemoGraphicAndMedicalHistoryBinding? = null
    val binding get() = _binding!!
    private var seg0: DemoGraphicAndMedicalHistorySeg0? = null
    private var seg1: DemoGraphicAndMedicalHistorySeg1? = null
    private var seg2: DemoGraphicAndMedicalHistorySeg2? = null
    private var seg3: DemoGraphicAndMedicalHistorySeg3? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDemoGraphicAndMedicalHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentDateAndSet()
        seg0 = DemoGraphicAndMedicalHistorySeg0(this)
        seg1 = DemoGraphicAndMedicalHistorySeg1(this)
        seg2 = DemoGraphicAndMedicalHistorySeg2(this)
        seg3 = DemoGraphicAndMedicalHistorySeg3(this)
        val validation = DemoGraphicAndMedicalHistoryValidation(this)

        binding.btnStartTrail.setOnClickListener {
            it.hideSoftKeyboard()
            if (validation.validate()) {
                getInputValueAndSet()
                val action = DemoGraphicAndMedicalHistoryFragmentDirections.actionDemoToTest()
                findNavController().navigate(action)
            } else {
                mainActivity.showSnackBar("All field are Required")
            }
        }
    }

    private fun getInputValueAndSet() {
        val educationYear = binding.includeSeg0.edtYearOfEducation.textToString()
        val edtDiabetesYear =
            binding.includeSeg1.includeDiabetesSubQuestions.edtDiabetesYear.textToString()
        val edtRegularlyMedicine = binding.includeSeg3.edtRegularlyMedicine.textToString()
        rawMeasurements.educationYear = educationYear
        rawMeasurements.hasDiabetesYears = edtDiabetesYear
        rawMeasurements.regularlyScheduledMedicines = edtRegularlyMedicine.toInt()
        rawMeasurements.subjectid = Random().nextInt(5)
    }

    private fun getCurrentDateAndSet() {
        val date = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(Date())
        rawMeasurements.testdate = date
        val age =
            (Calendar.getInstance(Locale.ENGLISH)[Calendar.YEAR] - rawMeasurements.selectedYear?.toInt()!!)
        binding.includeSeg0.edtDOB.setText(age.toString())
        binding.includeSeg0.edtDOB.isEnabled = false
        val color = ResourcesCompat.getColor(resources, R.color.blue_light, null)
        binding.includeSeg0.edtDOBLayout.boxBackgroundColor = color
    }

    fun getPrimaryColor(): Int {
        val value = TypedValue()
        requireContext().theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary, value, true
        )
        return value.data
    }

    fun getBackground(drawable: Int) = ContextCompat.getDrawable(requireContext(), drawable)
    fun getColor(color: Int) = ResourcesCompat.getColor(resources, color, null)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        seg0 = null
        seg1 = null
        seg2 = null
        seg3 = null
    }
}