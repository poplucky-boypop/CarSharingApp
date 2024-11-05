package com.example.carsharingapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // Проверяем, завершён ли онбординг
            val sharedPref = getSharedPreferences("Onboarding", MODE_PRIVATE)
            val onboardingComplete = sharedPref.getBoolean("onboarding_complete", false)

            if (onboardingComplete) {
                // Если онбординг завершён, переходим в MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Если онбординг не пройден, переходим на OnboardingActivity
                val intent = Intent(this, OnboardingActivity::class.java)
                startActivity(intent)
            }
            // Закрываем SplashActivity
            finish()
        }, 2000) // Задержка на 2 секунды
    }
}