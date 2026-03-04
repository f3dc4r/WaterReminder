package com.f3dc4r.waterreminder

import com.intellij.notification.*
import com.intellij.openapi.application.ApplicationManager
import java.util.concurrent.*

class WaterReminderService {

    private var scheduler: ScheduledExecutorService? = null

    companion object {
        private var instance: WaterReminderService? = null

        fun getInstance(): WaterReminderService {
            if (instance == null) instance = WaterReminderService()
            return instance!!
        }
    }

    fun start() {
        val settings = WaterReminderSettings.getInstance().state
        if (!settings.enabled) return

        scheduler = Executors.newSingleThreadScheduledExecutor()
        scheduler?.scheduleAtFixedRate(
            ::showNotification,
            settings.intervalMinutes.toLong(),
            settings.intervalMinutes.toLong(),
            TimeUnit.MINUTES
        )
    }

    fun restart() {
        stop()
        start()
    }

    fun stop() {
        scheduler?.shutdownNow()
        scheduler = null
    }

    private fun showNotification() {
        ApplicationManager.getApplication().invokeLater {
            NotificationGroupManager.getInstance()
                .getNotificationGroup("Water Reminder")
                .createNotification(
                    "💧 Drink water!",
                    "It's time to hydrate! Drink a glass of water.",
                    NotificationType.INFORMATION
                )
                .notify(null)
        }
    }
}