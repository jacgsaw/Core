package coe.cyberbank.sms_tools

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever

object SmsConstants {
    const val SMS_RETRIEVED_ACTION: String = SmsRetriever.SMS_RETRIEVED_ACTION
}

class SmsRetrieverManager(private val context: Context) {

    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null

    /**
     * Comienza a escuchar SMS usando SMS Retriever API
     */
    fun startListening(param: SmsBroadcastReceiver.SmsBroadcastListener) {
        val client = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()
        task.addOnSuccessListener {
            Log.d("tag", "SMS Retriever iniciado correctamente")
        }
        task.addOnFailureListener {
            Log.e("tag", "Error iniciando SMS Retriever", it)
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
}
