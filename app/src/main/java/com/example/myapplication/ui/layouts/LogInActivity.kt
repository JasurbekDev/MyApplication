package com.example.myapplication.ui.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val signUpFragment = SignUpFragment()
        supportFragmentManager.beginTransaction().replace(R.id.log_in_fragment_container, signUpFragment).commit()
    }
}