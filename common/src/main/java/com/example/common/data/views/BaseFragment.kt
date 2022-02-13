package com.example.common.data.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
    }

    private val dialog: android.app.Dialog by lazy {
        LoadingDialog.Builder(
            requireContext(),
            "",
            0,
            0,
            false
        ).build()
    }

    fun showToast(message: String) {
        hideLoading()
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoading() else hideLoading()
    }

    private fun hideLoading() {
        if (dialog.isShowing)
            dialog.cancel()
    }

    private fun showLoading() {
        if (!dialog.isShowing)
            dialog.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        dialog.dismiss()
    }

    open fun onBackPressed(){

    }
}