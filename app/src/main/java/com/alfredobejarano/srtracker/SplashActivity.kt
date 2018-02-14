package com.alfredobejarano.srtracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alfredobejarano.srtracker.home.view.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
