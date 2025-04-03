package com.jacgx.jacgsawmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
//import com.jacgx.corejac.CoreV1
import com.jacgx.jaccore.Corex
import coe.cyberbank.arxan.ArxanActivity
import coe.cyberbank.arxan.GuardReaction
import coe.cyberbank.arxan.ArxanDialog
import coe.cyberbank.arxan.databinding.DialogArxanBinding
import coe.cyberbank.arxan.databinding.ActivityArxanBinding

class MainActivity : AppCompatActivity() {

    private val core = Corex()
    //private val coreV1 = CoreV1()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("developer",core.getCore())
        //Log.d("developer",coreV1.getJac())

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            button.text = "Hola Mundo!"
            Toast.makeText(this, "Botón presionado", Toast.LENGTH_SHORT).show()
        }

        virtualControl()

    }

    fun virtualControl() {
        val customDialog = ArxanDialog(this, "#503-125")
        customDialog.show()
    }

}