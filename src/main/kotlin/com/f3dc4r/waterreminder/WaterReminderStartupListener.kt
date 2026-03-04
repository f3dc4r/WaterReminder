package com.f3dc4r.waterreminder

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class WaterReminderStartupListener : ProjectActivity {

    override suspend fun execute(project: Project) {
        WaterReminderService.getInstance().start()
    }
}