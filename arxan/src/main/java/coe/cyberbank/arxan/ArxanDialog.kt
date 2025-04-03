package coe.cyberbank.arxan

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.LayoutInflater
import android.view.Window
import coe.cyberbank.arxan.databinding.DialogArxanBinding

class ArxanDialog(
    private val context: Context,
    private val code: String
) : Dialog(context) {

    private lateinit var binding: DialogArxanBinding
    private val autoDismissHandler = Handler(context.mainLooper)
    private val autoDismissRunnable = Runnable {
        dismissDialogAndKillProcess()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogArxanBinding.inflate(LayoutInflater.from(context))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.dialogDescription.text = context.getString(R.string.alert_guard, code)

        setCancelable(false)
        autoDismissHandler.postDelayed(autoDismissRunnable, 10000L)
        binding.buttonDav.setOnClickListener {
            dismissDialogAndKillProcess()
        }
    }

    private fun dismissDialogAndKillProcess() {
        dismiss()
        Process.killProcess(Process.myPid())
    }

    override fun dismiss() {
        super.dismiss()
        autoDismissHandler.removeCallbacks(autoDismissRunnable)
    }
}
