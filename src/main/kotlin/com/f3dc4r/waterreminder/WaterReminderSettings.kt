package com.f3dc4r.waterreminder

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.*

@State(name = "WaterReminderSettings", storages = [Storage("WaterReminder.xml")])
class WaterReminderSettings : PersistentStateComponent<WaterReminderSettings.State> {

    data class State(
        var intervalMinutes: Int = 30,
        var enabled: Boolean = true
    )

    private var state = State()

    companion object {
        fun getInstance(): WaterReminderSettings =
            ApplicationManager.getApplication().getService(WaterReminderSettings::class.java)
    }

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }
}