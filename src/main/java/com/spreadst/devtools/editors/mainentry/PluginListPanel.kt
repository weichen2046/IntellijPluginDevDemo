package com.spreadst.devtools.editors.mainentry

import java.awt.Color
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel

class PluginListPanel(title: String) : JPanel() {

    private var titleLabel = JLabel()

    init {
        layout = GridBagLayout()
        val c = GridBagConstraints()

        titleLabel.text = title
        titleLabel.horizontalAlignment = JLabel.CENTER
        titleLabel.font = titleLabel.font.deriveFont(titleLabel.font.style or Font.BOLD)

        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        c.gridx = 0

        c.gridy = 0
        c.ipady = 20
        add(titleLabel, c)

        c.gridy = 1
        c.ipady = 0
        c.weighty = 1.0
        val panel = JPanel()
        // FIXME: remove the following debug line
        panel.background = Color.BLUE
        add(panel, c)
    }

    fun setTitle(title: String) {
        titleLabel.text = title
    }

    fun update() {
        // TODO: update plugin list
    }
}