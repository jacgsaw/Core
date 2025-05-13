package coe.cyberbank.sms_tools

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.phone.SmsRetriever

open class SmsBroadcastReceiver(
    private val listener: SmsBroadcastListener? = null
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("tag", "onReceive calling listener es null? ${listener == null}")
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action) {
            val extras = intent.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as? com.google.android.gms.common.api.Status

            //Log.d("tag", "Status del SMS Retriever: $status")

            when (status?.statusCode) {
                com.google.android.gms.common.api.CommonStatusCodes.SUCCESS -> {
                    // Mensaje SMS recibido exitosamente
                    val message = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as? String
                    Log.d("tag", "SMS Received: $message")
                    listener?.onSmsReceived(message ?: "")
                }
                com.google.android.gms.common.api.CommonStatusCodes.TIMEOUT -> {
                    // No se recibió ningún SMS (timeout)
                    Log.d("tag", "SMS Retriever timeout")
                    listener?.onTimeout()
                }
                else -> {
                    Log.d("tag", "Other status received: ${status?.statusCode}")
                }
            }
        }
    }

    interface SmsBroadcastListener {
        fun onSmsReceived(message: String)
        fun onTimeout()
    }
}
