package com.jacgx.jacgsawmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jacgx.corejac.CoreV1
import com.jacgx.jaccore.Corev

class MainActivity : AppCompatActivity() {

    private val core = Corev()
    private val coreV1 = CoreV1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("developer",core.getCore())
        Log.d("developer",coreV1.getJac())
    }
}