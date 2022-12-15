package com.isaacsufyan.catchu.presentation.fragments.language

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isaacsufyan.catchu.R
import com.isaacsufyan.catchu.adapter.MaterialArrayAdapter
import com.isaacsufyan.catchu.base.BaseFragment
import com.isaacsufyan.catchu.databinding.FragmentLanguageBinding
import com.isaacsufyan.catchu.domain.enum.ELanguage
import com.isaacsufyan.catchu.presentation.fragments.SharedViewModel


class LanguageFragment : BaseFragment() {

    private val viewModel by activityViewModels<SharedViewModel>()
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    private var TAG = "TAG_Language"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "onAttach: Language" )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchPurposeList.observe(viewLifecycleOwner) { setupResearchPurpose(it) }
        viewModel.languageList.observe(viewLifecycleOwner) { setupLanguageDropDown(it) }
        binding.btnContinue.setOnClickListener {
            rawMeasurements.researchPurpose = binding.edtResearchPurpose.text.toString()
            val action =
                if (viewModel.token == null) LanguageFragmentDirections.actionLanguageToCommunityInd()
                else LanguageFragmentDirections.actionLanguageToProfileDetails()
            findNavController().navigate(action)
        }
    }

    private fun setupLanguageDropDown(list: List<String>) {
        val materialAdapter = MaterialArrayAdapter(
            requireContext(),
            R.layout.simple_list_item,
            list,
            binding.edtLanguageView.simpleItemSelectedColor,
            binding.edtLanguageView.simpleItemSelectedRippleColor
        )
        binding.edtLanguageView.setAdapter(materialAdapter)

        val selectedText = if (mainActivity.selectedLanguage == ELanguage.ENGLISH) {
            binding.edtLanguageView.adapter.getItem(0).toString()
        } else {
            binding.edtLanguageView.adapter.getItem(1).toString()
        }

        binding.edtLanguageView.setText(selectedText, false)
        materialAdapter.updateColor(selectedText)

        binding.edtLanguageView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                if (position == 0 && mainActivity.selectedLanguage != ELanguage.ENGLISH) {
                    viewModel.setLanguage(ELanguage.ENGLISH.language)
                    mainActivity.setLocale(ELanguage.ENGLISH.language)
                    mainActivity.restartApp()
                } else if (position == 1 && mainActivity.selectedLanguage != ELanguage.SPANISH) {
                    viewModel.setLanguage(ELanguage.SPANISH.language)
                    mainActivity.setLocale(ELanguage.SPANISH.language)
                    mainActivity.restartApp()
                }
            }
    }

    private fun setupResearchPurpose(list: List<String>) {
        val materialAdapter = MaterialArrayAdapter(
            requireContext(),
            R.layout.simple_list_item,
            list,
            binding.edtResearchPurpose.simpleItemSelectedColor,
            binding.edtResearchPurpose.simpleItemSelectedRippleColor
        )
        binding.edtResearchPurpose.setAdapter(materialAdapter)
        var selectedText = binding.edtResearchPurpose.adapter.getItem(0).toString()
        binding.edtResearchPurpose.setText(selectedText, false)
        materialAdapter.updateColor(selectedText)

        binding.edtResearchPurpose.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                selectedText = binding.edtResearchPurpose.adapter.getItem(position).toString()
                materialAdapter.updateColor(selectedText)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}