package com.f3dc4r.waterreminder

import com.intellij.openapi.options.Configurable
import javax.swing.*
import java.awt.*

class WaterReminderConfigurable : Configurable {

    private lateinit var panel: JPanel
    private lateinit var intervalSpinner: JSpinner
    private lateinit var enabledCheckBox: JCheckBox

    override fun getDisplayName() = "Water Reminder"

    override fun createComponent(): JComponent {
        val innerPanel = JPanel()
        innerPanel.layout = BoxLayout(innerPanel, BoxLayout.Y_AXIS)
        innerPanel.border = BorderFactory.createEmptyBorder(8, 8, 8, 8)

        enabledCheckBox = JCheckBox("Enable water reminder")
        enabledCheckBox.alignmentX = Component.LEFT_ALIGNMENT
        innerPanel.add(enabledCheckBox)

        innerPanel.add(Box.createVerticalStrut(8))

        val freqPanel = JPanel(FlowLayout(FlowLayout.LEFT, 0, 0))
        freqPanel.alignmentX = Component.LEFT_ALIGNMENT
        freqPanel.add(JLabel("Frequency (minutes): "))
        intervalSpinner = JSpinner(SpinnerNumberModel(30, 1, 480, 5))
        intervalSpinner.preferredSize = Dimension(70, intervalSpinner.preferredSize.height)
        freqPanel.add(intervalSpinner)
        innerPanel.add(freqPanel)

        // Wrapper allineato in alto a sinistra
        panel = JPanel(BorderLayout())
        panel.add(innerPanel, BorderLayout.NORTH)
        innerPanel.alignmentX = Component.LEFT_ALIGNMENT

        reset()
        return panel
    }

    override fun isModified(): Boolean {
        val s = WaterReminderSettings.getInstance().state
        return s.intervalMinutes != intervalSpinner.value as Int
                || s.enabled != enabledCheckBox.isSelected
    }

    override fun apply() {
        val s = WaterReminderSettings.getInstance().state
        s.intervalMinutes = intervalSpinner.value as Int
        s.enabled = enabledCheckBox.isSelected
        WaterReminderService.getInstance().restart()

        // Forza aggiornamento icona nella status bar
        val projectManager = com.intellij.openapi.project.ProjectManager.getInstance()
        projectManager.openProjects.forEach { project ->
            val statusBar = com.intellij.openapi.wm.WindowManager.getInstance().getStatusBar(project)
            statusBar?.updateWidget("WaterReminderWidget")
        }
    }

    override fun reset() {
        val s = WaterReminderSettings.getInstance().state
        intervalSpinner.value = s.intervalMinutes
        enabledCheckBox.isSelected = s.enabled
    }
}