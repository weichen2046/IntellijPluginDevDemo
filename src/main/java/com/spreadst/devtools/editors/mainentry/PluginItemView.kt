package com.spreadst.devtools.editors.mainentry

import com.intellij.ui.SizedIcon
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.labels.LinkLabel
import com.intellij.ui.components.panels.VerticalBox
import icons.Icons
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JPanel

class PluginItemView(actions: List<Pair<String, Runnable>>? = null) : JPanel() {
    private val titleLabel = JBLabel()
    private val contentLabel = JBLabel()
    private var actionsPanel = VerticalBox()

    init {
        layout = GridBagLayout()
        val c = GridBagConstraints()
        c.insets.left = 5

        // add icon label
        c.anchor = GridBagConstraints.WEST
        val iconLabel = JBLabel(SizedIcon(Icons.CE_64, ICON_SIZE, ICON_SIZE))
        add(iconLabel, c)

        val centerPanel = JPanel(GridBagLayout())
        val c1 = GridBagConstraints()
        c1.fill = GridBagConstraints.BOTH
        c1.weightx = 1.0
        c1.weighty = 1.0
        c1.gridx = 0
        c1.gridy = 0
        centerPanel.add(titleLabel, c1)
        c1.gridy = 1
        centerPanel.add(contentLabel, c1)
        // add center text
        c.anchor = GridBagConstraints.CENTER
        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        c.weighty = 1.0
        add(centerPanel, c)

        if (actions != null && actions.isNotEmpty()) {
            for ((first, second) in actions) {
                actionsPanel.add(LinkLabel.create(first, second))
            }
        }
        // add right actions
        c.anchor = GridBagConstraints.EAST
        c.fill = GridBagConstraints.NONE
        c.weightx = 0.0
        c.weighty = 0.0
        c.insets.right = 5
        add(actionsPanel, c)
    }

    fun setTitle(title: String) {
        titleLabel.text = title
    }

    fun setContent(content: String) {
        contentLabel.text = content
    }

    companion object {
        const val ICON_SIZE = 64;
    }
}