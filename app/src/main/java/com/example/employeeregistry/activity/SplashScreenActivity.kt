package com.example.employeeregistry.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.employeeregistry.R
import com.example.employeeregistry.util.SharedPreferenceUtil
import com.example.employeeregistry.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity(), SplashScreenViewModel.NetworkFailureListener {

    val SPLASH_SCREEN_DELAY = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SharedPreferenceUtil.init(this)
        val splashScreenViewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        splashScreenViewModel.init(this, this)
        splashScreenViewModel.getAPIKey("","")
        splashScreenViewModel.loginLiveData.observe(this, Observer<String> {
            GlobalScope.launch {
                delay(SPLASH_SCREEN_DELAY)
                SharedPreferenceUtil.write(SharedPreferenceUtil.API_KEY, it)
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }

    override fun onNetworkFailure(error: String?) {
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_LONG
        ).show()
    }
}
