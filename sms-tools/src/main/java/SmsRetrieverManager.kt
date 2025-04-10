package coe.cyberbank.sms_tools

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever

object SmsConstants {
    const val SMS_RETRIEVED_ACTION: String = SmsRetriever.SMS_RETRIEVED_ACTION
}

class SmsRetrieverManager(private val context: Context) {

    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    private var isReceiverRegistered = false

    /**
     * Comienza a escuchar SMS usando SMS Retriever API
     */
    fun startListening(listener: SmsBroadcastReceiver.SmsBroadcastListener) {
        val client = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Log.d("tag", "SMS Retriever iniciado correctamente")

            if (!isReceiverRegistered) {
                registerReceiver(listener)
            }
        }
        task.addOnFailureListener { exception ->
            Log.e("tag", "Error iniciando SMS Retriever", exception)
        }
    }

    /**
     * Detiene la escucha de SMS
     */
    fun stopListening() {
        smsBroadcastReceiver?.let {
            try {
                context.unregisterReceiver(it)
                Log.d("tag", "Receiver desregistrado exitosamente")
            } catch (e: Exception) {
                Log.e("tag", "Error al desregistrar receiver", e)
            }
            smsBroadcastReceiver = null
        }
    }

    private fun registerReceiver(listener: SmsBroadcastReceiver.SmsBroadcastListener) {
        smsBroadcastReceiver = SmsBroadcastReceiver(listener)
        val intentFilter = IntentFilter(SmsConstants.SMS_RETRIEVED_ACTION)

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(
                    smsBroadcastReceiver,
                    intentFilter,
                    Context.RECEIVER_EXPORTED
                )
            } else {
                @Suppress("DEPRECATION")
                ContextCompat.registerReceiver(
                    context,
                    smsBroadcastReceiver,
                    intentFilter,
                    ContextCompat.RECEIVER_EXPORTED
                )
            }
            isReceiverRegistered = true
            Log.d("SmsRetrieverManager", "Receiver registrado exitosamente")
        } catch (e: Exception) {
            Log.e("SmsRetrieverManager", "Error al registrar Receiver", e)
        }
    }
}
