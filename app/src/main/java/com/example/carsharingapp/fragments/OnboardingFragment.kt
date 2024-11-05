package com.example.carsharingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.carsharingapp.R


class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutResId = arguments?.getInt(ARG_LAYOUT_RES_ID) ?: R.layout.first_page
        return inflater.inflate(layoutResId, container, false)
    }

    companion object {
        private const val ARG_LAYOUT_RES_ID = "layoutResId"

        fun newInstance(layoutResId: Int): OnboardingFragment {
            val fragment = OnboardingFragment()
            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            fragment.arguments = args
            return fragment
        }
    }
}

