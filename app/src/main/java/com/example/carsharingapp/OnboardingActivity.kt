package com.example.carsharingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.carsharingapp.adapters.OnboardingAdapter
import com.google.android.material.tabs.TabLayout

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    //private lateinit var tabLayout: TabLayout
    private lateinit var btnNext: Button
    //private lateinit var btnSkip: Button
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding)

        viewPager = findViewById(R.id.viewPager)
        //tabLayout = findViewById(R.id.dotsIndicator)
        btnNext = findViewById(R.id.btnNext)
        //btnSkip = findViewById(R.id.btnSkip)

        adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        //TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        //btnSkip.setOnClickListener {
        //    finishOnboarding()
        //}

        btnNext.setOnClickListener {
            if (viewPager.currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            } else {
                finishOnboarding()
            }
        }
    }

    private fun finishOnboarding() {
        val sharedPref = getSharedPreferences("Onboarding", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("onboarding_complete", true)
            apply()
        }
        // Завершаем онбординг и переходим в главное приложение
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}