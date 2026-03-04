package com.f3dc4r.waterreminder

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.openapi.wm.*
import com.intellij.util.Consumer
import java.awt.event.MouseEvent

class WaterStatusBarWidget(private val project: Project) :
    StatusBarWidget, StatusBarWidget.IconPresentation {

    override fun ID() = "WaterReminderWidget"

    override fun getIcon(): javax.swing.Icon {
        val s = WaterReminderSettings.getInstance().state
        return if (s.enabled)
            IconLoader.getIcon("/icons/water_drop.svg", javaClass)
        else
            IconLoader.getIcon("/icons/water_drop_disabled.svg", javaClass)
    }

    override fun getTooltipText(): String {
        val s = WaterReminderSettings.getInstance().state
        return if (s.enabled)
            "💧 Water Reminder — every ${s.intervalMinutes} min"
        else
            "💧 Water Reminder — disabled"
    }

    override fun getClickConsumer() = Consumer<MouseEvent> {
        com.intellij.openapi.options.ShowSettingsUtil.getInstance()
            .showSettingsDialog(project, WaterReminderConfigurable::class.java)
    }

    override fun getPresentation(): StatusBarWidget.WidgetPresentation = this

    override fun install(statusBar: StatusBar) {}

    override fun dispose() {}
}