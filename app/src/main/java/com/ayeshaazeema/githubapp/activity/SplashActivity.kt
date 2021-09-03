package com.ayeshaazeema.githubapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ayeshaazeema.githubapp.R
import com.ayeshaazeema.githubapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}