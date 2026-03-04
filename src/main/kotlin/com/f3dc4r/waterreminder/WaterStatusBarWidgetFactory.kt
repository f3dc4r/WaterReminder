package com.f3dc4r.waterreminder

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.*

class WaterStatusBarWidgetFactory : StatusBarWidgetFactory {

    override fun getId() = "WaterReminderWidget"

    override fun getDisplayName() = "Water Reminder"

    override fun isAvailable(project: Project) = true

    override fun createWidget(project: Project): StatusBarWidget {
        return WaterStatusBarWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) {
        widget.dispose()
    }

    override fun canBeEnabledOn(statusBar: StatusBar) = true
}

