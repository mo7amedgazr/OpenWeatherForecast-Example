package com.example.upcomings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.common.data.views.BaseFragment
import com.example.upcomings.databinding.FragmentUpcomingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingsFragment : BaseFragment() {

    private val mViewModel: UpcomingWeatherViewModel by viewModels()
    private val mAdapter by lazy { UpcomingListAdapter() }

    private var _binding: FragmentUpcomingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initUpcomingList()
    }

    private fun initViewModel() {
        mViewModel.upcomingWeatherLiveData.observe(viewLifecycleOwner) { list ->
            mAdapter.submitList(list)
        }
        mViewModel.showError.observe(viewLifecycleOwner) { error ->
            error?.let { showToast(it) }
        }
        mViewModel.showLoading.observe(viewLifecycleOwner) { isLoading ->
            handleLoading(isLoading)
        }

        mViewModel.getUpcomingWeather()
    }

    private fun initUpcomingList() {
        binding.rvUpcomingWeather.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}