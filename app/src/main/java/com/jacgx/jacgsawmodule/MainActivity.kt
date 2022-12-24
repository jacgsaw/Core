package com.jacgx.jacgsawmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jacgx.jaccore.Core

class MainActivity : AppCompatActivity() {

    private val core = Core()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("jacgsaw",getHello())
    }
}