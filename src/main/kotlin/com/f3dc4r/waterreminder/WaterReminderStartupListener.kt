package com.f3dc4r.waterreminder

import com.intellij.ide.AppLifecycleListener

class WaterReminderStartupListener : AppLifecycleListener {

    override fun appStarted() {
        WaterReminderService.getInstance().start()
    }

    override fun appClosing() {
        WaterReminderService.getInstance().stop()
    }
}