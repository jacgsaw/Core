package coe.cyberbank.sms_tools

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.phone.SmsRetriever

object SmsConstants {
    const val SMS_RETRIEVED_ACTION: String = SmsRetriever.SMS_RETRIEVED_ACTION
}

class SmsRetrieverManager(private val context: Context) {

    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    private var isReceiverRegistered = false
    private var timeoutHandler: Handler? = null
    private var timeoutRunnable: Runnable? = null

    /**
     * Comienza a escuchar SMS usando SMS Retriever API
     */
    fun startListening(listener: SmsBroadcastReceiver.SmsBroadcastListener) {
        val client = SmsRetriever.getClient(context)
        val task = client.startSmsRetriever()

        task.addOnSuccessListener {
            Log.d("tag", "SMS Retriever init ok")

            if (!isReceiverRegistered) {
                registerReceiver(listener)
                startManualTimeout(listener)
            }
        }

        task.addOnFailureListener { exception ->
            Log.e("tag", "Error init SMS Retriever", exception)
        }
    }

    /**
     * Registra el BroadcastReceiver dinámicamente
     */
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
            Log.d("tag", "Receiver register is ok")
        } catch (e: Exception) {
            Log.e("tag4", "Error register Receiver", e)
        }
    }

    /**
     * Inicia un timeout manual por seguridad
     */
    private fun startManualTimeout(listener: SmsBroadcastReceiver.SmsBroadcastListener) {
        timeoutHandler = Handler(Looper.getMainLooper())
        timeoutRunnable = Runnable {
            Log.d("tag", "Timeout is exit")
            listener.onTimeout()
            stopListening()
        }
        timeoutHandler?.postDelayed(timeoutRunnable!!, 2 * 60 * 1000) // 2 minutos
    }

    /**
     * Detiene la escucha de SMS
     */
    fun stopListening() {
        timeoutHandler?.removeCallbacks(timeoutRunnable!!)
        timeoutHandler = null
        timeoutRunnable = null

        smsBroadcastReceiver?.let {
            if (isReceiverRegistered) {
                try {
                    context.unregisterReceiver(it)
                    Log.d("tag", "Receiver exit ok")
                } catch (e: Exception) {
                    Log.e("tag", "Error exit receiver", e)
                }
                isReceiverRegistered = false
                smsBroadcastReceiver = null
            }
        }
    }
}
