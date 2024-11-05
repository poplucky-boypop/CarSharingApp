package com.example.carsharingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        //installSplashScreen()
        enableEdgeToEdge()

        val sharedPref = getSharedPreferences("Onboarding", MODE_PRIVATE)
        val onboardingComplete = sharedPref.getBoolean("onboarding_complete", false)

        if (!onboardingComplete) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_getting_started)
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
        //}

        val btnStartLogin = findViewById<android.widget.Button>(R.id.btnStartLogin)

        btnStartLogin.setOnClickListener {
            //val intent = Intent(this, LoginActivity::class.java)
            val intent = Intent(this, ProgramMenuActivity::class.java)
            startActivity(intent)
        }

        val btnStartReg = findViewById<android.widget.Button>(R.id.btnStartRegistration)

        btnStartReg.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}