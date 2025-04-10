package coe.cyberbank.sms_tools

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import java.security.MessageDigest

object AppSignatureHelper {

    fun getAppSignatures(context: Context): List<String> {
        val appCodes = mutableListOf<String>()

        try {
            val packageName = context.packageName
            val packageManager = context.packageManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // Android 9 (API 28) o mayor
                val signatures = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo?.apkContentsSigners
                if (signatures != null) {
                    for (signature in signatures) {
                        val hash = hash(packageName, signature.toByteArray())
                        if (hash != null) {
                            appCodes.add(hash)
                        }
                    }
                }
            } else {
                // Android 7 (API 24) a 8 (API 27)
                @Suppress("DEPRECATION")
                val signatures = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures
                if (signatures != null) {
                    for (signature in signatures) {
                        val hash = hash(packageName, signature.toByteArray())
                        if (hash != null) {
                            appCodes.add(hash)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return appCodes
    }

    private fun hash(packageName: String, signature: ByteArray): String? {
        try {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val appInfo = "$packageName ${Base64.encodeToString(signature, Base64.NO_WRAP)}"
            messageDigest.update(appInfo.toByteArray(Charsets.UTF_8))
            val digest = messageDigest.digest()

            val base64Hash = Base64.encodeToString(digest, Base64.NO_WRAP)
            return base64Hash.substring(0, 11)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
