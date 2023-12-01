package com.example.ecstore.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.ecstore.databinding.SplashActivityBinding

class SplashActivity : AppCompatActivity() {
    private val binding by lazy {SplashActivityBinding.inflate(layoutInflater)}
    private val splashTimeOut: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}