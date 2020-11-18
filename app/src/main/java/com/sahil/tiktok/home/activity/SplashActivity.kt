package com.sahil.tiktok.home.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import com.sahil.tiktok.R
import com.sahil.tiktok.utils.UserPreferences
import com.sahil.tiktok.login.GoogleLogin

class SplashActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        userPreferences = UserPreferences(this)

        Handler(Looper.getMainLooper()).postDelayed({

            userPreferences.bookmark.asLiveData().observe(this, {

                if (it == null){
                    val intent = Intent(this, GoogleLogin::class.java)
                    startActivity(intent)
                    finish()
                }

                it?.let {
                    if (it){
                        val intent = Intent(this, VideoPlayerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this, GoogleLogin::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            })

        },2000)

    }
}