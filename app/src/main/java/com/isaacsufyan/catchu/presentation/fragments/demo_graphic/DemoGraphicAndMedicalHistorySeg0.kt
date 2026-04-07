package com.isaacsufyan.catchu.presentation.fragments.demo_graphic

import android.view.View
import android.widget.AdapterView
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.adapter.MaterialArrayAdapter
import com.isaacsufyan.catchu.domain.enum.EAnswer
import com.isaacsufyan.catchu.domain.enum.EGender
import com.isaacsufyan.catchu.domain.model.RawMeasurements
import com.isaacsufyan.catchu.utils.hideSoftKeyboard

class DemoGraphicAndMedicalHistorySeg0(
    var fragment: DemoGraphicAndMedicalHistoryFragment
) : View.OnClickListener {

    private var binding = fragment.binding.includeSeg0

    init {
        selectGender(EGender.MALE)
        setupRadicalCategory()
        setListener()
    }

    private fun setListener() {
        binding.tvGenderMale.setOnClickListener(this)
        binding.tvGenderFemale.setOnClickListener(this)
        binding.tvGenderOther.setOnClickListener(this)

        binding.tvHispanicLatinoYes.setOnClickListener(this)
        binding.tvHispanicLatinoYNo.setOnClickListener(this)

        binding.edtYearOfEducation.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) {
                v.hideSoftKeyboard()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.tvGenderMale.id -> {
                selectGender(EGender.MALE)
            }
            binding.tvGenderFemale.id -> {
                selectGender(EGender.FEMALE)
            }
            binding.tvGenderOther.id -> {
                selectGender(EGender.OTHER)
            }

            binding.tvHispanicLatinoYes.id -> {
                setHispanicOrLatina(EAnswer.YES)
            }
            binding.tvHispanicLatinoYNo.id -> {
                setHispanicOrLatina(EAnswer.NO)
            }
        }
    }

    private fun selectGender(eGender: EGender) {
        fragment.rawMeasurements.selectedGender = eGender.value
        when (eGender) {
            EGender.MALE -> {
                binding.tvGenderMale.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvGenderFemale.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)
                binding.tvGenderOther.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvGenderMale.setTextColor(fragment.getColor(R.color.white))
                binding.tvGenderFemale.setTextColor(fragment.getPrimaryColor())
                binding.tvGenderOther.setTextColor(fragment.getPrimaryColor())
            }
            EGender.FEMALE -> {
                binding.tvGenderFemale.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvGenderMale.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)
                binding.tvGenderOther.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvGenderFemale.setTextColor(fragment.getColor(R.color.white))
                binding.tvGenderMale.setTextColor(fragment.getPrimaryColor())
                binding.tvGenderOther.setTextColor(fragment.getPrimaryColor())
            }
            EGender.OTHER -> {
                binding.tvGenderOther.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvGenderFemale.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)
                binding.tvGenderMale.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvGenderOther.setTextColor(fragment.getColor(R.color.white))
                binding.tvGenderFemale.setTextColor(fragment.getPrimaryColor())
                binding.tvGenderMale.setTextColor(fragment.getPrimaryColor())
            }
        }
    }

    private fun setupRadicalCategory() {
        val radicaCatList = listOf(
            "American Indian/Alaska Native",
            "Asian",
            "Native Hawaiian or Other Pacific Island",
            "Black/African American",
            "White",
            "More than one race",
            "Unknown/Prefer not to say"
        )
        val materialAdapter = MaterialArrayAdapter(
            fragment.requireContext(),
            R.layout.simple_list_item,
            radicaCatList,
            binding.edtRadicalCategory.simpleItemSelectedColor,
            binding.edtRadicalCategory.simpleItemSelectedRippleColor
        )
        binding.edtRadicalCategory.setAdapter(materialAdapter)

        binding.edtRadicalCategory.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedText = radicaCatList[position]
                fragment.rawMeasurements.selectedRace = position + 1
                materialAdapter.updateColor(selectedText)
            }
    }

    fun applyPrefill(data: RawMeasurements) {
        // Gender: "1"=Male, "2"=Female, "3"=Other
        data.selectedGender?.let { gender ->
            when (gender) {
                EGender.MALE.value -> selectGender(EGender.MALE)
                EGender.FEMALE.value -> selectGender(EGender.FEMALE)
                EGender.OTHER.value -> selectGender(EGender.OTHER)
            }
        }

        // Race: API returns 1-7 mapping to the list index (position + 1)
        data.selectedRace?.let { race ->
            val raceList = listOf(
                "American Indian/Alaska Native",
                "Asian",
                "Native Hawaiian or Other Pacific Island",
                "Black/African American",
                "White",
                "More than one race",
                "Unknown/Prefer not to say"
            )
            if (race in 1..raceList.size) {
                val raceText = raceList[race - 1]
                binding.edtRadicalCategory.setText(raceText, false)
                fragment.rawMeasurements.selectedRace = race
            }
        }

        // Hispanic/Latino: 0=No, 1=Yes
        data.isHispanicOrLatin?.let { hispanic ->
            setHispanicOrLatina(if (hispanic == EAnswer.YES.value) EAnswer.YES else EAnswer.NO)
        }

        // Education year
        data.educationYear?.let { year ->
            binding.edtYearOfEducation.setText(year)
            fragment.rawMeasurements.educationYear = year
        }
    }

    private fun setHispanicOrLatina(eAnswer: EAnswer) {
        fragment.rawMeasurements.isHispanicOrLatin = eAnswer.value
        binding.tvHispanicLatinoError.visibility = View.GONE
        when (eAnswer) {
            EAnswer.NO -> {
                binding.tvHispanicLatinoYNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHispanicLatinoYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHispanicLatinoYNo.setTextColor(fragment.getColor(R.color.white))
                binding.tvHispanicLatinoYes.setTextColor(fragment.getPrimaryColor())
            }
            EAnswer.YES -> {
                binding.tvHispanicLatinoYes.background =
                    fragment.getBackground(R.drawable.view_bg_round_filled_navy)
                binding.tvHispanicLatinoYNo.background =
                    fragment.getBackground(R.drawable.view_bg_round_outline_navy)

                binding.tvHispanicLatinoYes.setTextColor(fragment.getColor(R.color.white))
                binding.tvHispanicLatinoYNo.setTextColor(fragment.getPrimaryColor())
            }
        }
    }
}