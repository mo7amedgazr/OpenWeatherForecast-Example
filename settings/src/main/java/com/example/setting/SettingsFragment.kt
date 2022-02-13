package com.example.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.common.data.preferences.Units
import com.example.setting.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val mViewModel: SettingsViewModel by viewModels()

    companion object {
        const val REQUEST_KEY = "Settings_Fragment_REQUEST_KEY"
        const val UNIT_UPDATED_KEY = "unit_updated"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        val selectedUnit = mViewModel.getSelectedUnit()
        if (selectedUnit == Units.FAHRENHEIT) {
            binding.rbFahrenheit.isChecked = true
        } else {
            binding.rbCelsius.isChecked = true
        }


        binding.btnSave.setOnClickListener {
            onSaveButtonClicked()
        }
    }

    private fun onSaveButtonClicked() {
        val preSelectedUnit = mViewModel.getSelectedUnit()
        val newSelectedUnit = if (binding.rbCelsius.isChecked) Units.CELSIUS else Units.FAHRENHEIT

        if (preSelectedUnit != newSelectedUnit) {
            mViewModel.updateUnit(newSelectedUnit.unit)
            setFragmentResult(REQUEST_KEY, bundleOf(UNIT_UPDATED_KEY to true))
        } else {
            setFragmentResult(REQUEST_KEY, bundleOf(UNIT_UPDATED_KEY to false))
        }

        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}