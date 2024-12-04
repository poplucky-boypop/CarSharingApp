package com.example.carsharingapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.carsharingapp.adapters.ProgramMenuAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProgramMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_program_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewPager: ViewPager2 = findViewById(R.id.vp2ProgramMenu)
        val tabLayout: TabLayout = findViewById(R.id.tlProgramMenu)

        viewPager.adapter = ProgramMenuAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_home)
                1 -> tab.setIcon(R.drawable.ic_bookmark)
                2 -> tab.setIcon(R.drawable.ic_settings)
            }
        }.attach()

        viewPager.setPageTransformer { page, position ->
            page.requestLayout()
        }

        fun onSideElement() {
            // Скрываем элемент, например, TextView с id `textViewToHide`
            findViewById<com.google.android.material.tabs.TabLayout>(R.id.tlProgramMenu)?.visibility = View.VISIBLE
        }
    }
}