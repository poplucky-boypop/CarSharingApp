package com.example.carsharingapp.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carsharingapp.fragments.FavoritesFragment
import com.example.carsharingapp.fragments.HomePageFragment
import com.example.carsharingapp.fragments.SettingsFragment

class ProgramMenuAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomePageFragment()
            1 -> FavoritesFragment()
            2 -> SettingsFragment()
            else -> HomePageFragment()
        }
    }
}