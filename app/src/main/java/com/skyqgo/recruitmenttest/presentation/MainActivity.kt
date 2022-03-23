package com.skyqgo.recruitmenttest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skyqgo.recruitmenttest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}