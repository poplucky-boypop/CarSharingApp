package com.example.carsharingapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carsharingapp.R
import com.example.carsharingapp.fragments.OnboardingFragment


class OnboardingAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    // Указываем количество слайдов
    override fun getItemCount(): Int = 3

    // Создаем фрагмент на основе позиции
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment.newInstance(R.layout.first_page)
            1 -> OnboardingFragment.newInstance(R.layout.second_page)
            2 -> OnboardingFragment.newInstance(R.layout.third_page)
            else -> throw IllegalStateException("Неверная позиция: $position")
        }
    }
}