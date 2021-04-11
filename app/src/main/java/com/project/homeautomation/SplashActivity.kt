package com.project.homeautomation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.project.homeautomation.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // change activity here
        val intent = Intent(this, MainActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            this.startActivity(intent)
            this.finish()
        }, 1200)
    }


}