package com.jacgx.jacgsawmodule

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coe.cyberbank.arxan.ArxanDialog
import coe.cyberbank.sms_tools.AppSignatureHelperV1
import coe.cyberbank.sms_tools.GatewaySms
import coe.cyberbank.sms_tools.SmsBroadcastReceiver
import coe.cyberbank.sms_tools.SmsRetrieverManager
import com.jacgx.jaccore.Corex

class MainActivity : AppCompatActivity() {

    private lateinit var smsRetrieverManager: SmsRetrieverManager
    private var receivedOtp: String? = null

    private val core = Corex()
    private val sms = GatewaySms()
    private val appSignatureHelper = AppSignatureHelperV1(this)

    private fun startSmsRetriever() {
        smsRetrieverManager.startListening(object : SmsBroadcastReceiver.SmsBroadcastListener {
            override fun onSmsReceived(message: String) {
                Log.d("tag1", "Mensaje recibido: $message")
                val otpCode = extractOtpFromMessage(message)
                if (otpCode != null) {
                    Log.d("tag2", "Código extraído: $otpCode")
                    receivedOtp = otpCode
                } else {
                    Log.d("tag3", "No se encontró código en el mensaje")
                }

                // Opcional: detener el listener cuando recibes el SMS
                smsRetrieverManager.stopListening()
            }

            override fun onTimeout() {
                Log.d("tag4", "No se recibió SMS (timeout)")
                smsRetrieverManager.stopListening()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        smsRetrieverManager = SmsRetrieverManager(this)

        Log.d("tag5", core.getCore())

        val button = findViewById<Button>(R.id.button)

        // Obtener el hash de la app
        Log.d("tag6", "get sms: ${sms.getSms()}")
        Log.e("tag7", "key: ${appSignatureHelper.appSignatures}")

        button.setOnClickListener {
            button.text = "Hi ${sms.getSms()}"
            Toast.makeText(this, "Botón presionado: ${receivedOtp}", Toast.LENGTH_SHORT).show()
        }

        // Comenzar a escuchar SMS
        startSmsRetriever()
    }

    private fun virtualControl() {
        val customDialog = ArxanDialog(this, "#503-125")
        customDialog.show()
    }

    private fun extractOtpFromMessage(message: String): String? {
        val pattern = Regex("\\b\\d{6}\\b")
        val matchResult = pattern.find(message)
        return matchResult?.value
    }
}
