package com.spreadst.devtools.demos.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import icons.Icons
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel

class DemoToolWindowFactory : ToolWindowFactory {
    private var toolwindow: ToolWindow? = null

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolwindow = toolWindow
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(createToolWindowPanel(), getNextTabDisplayName(), false)
        toolwindow!!.contentManager.addContent(content)
    }

    private fun createToolWindowPanel(): JComponent {
        val panel = JPanel(BorderLayout())
        panel.add(getToolBar(), BorderLayout.WEST)
        panel.add(JButton("Center Component"), BorderLayout.CENTER)
        return panel
    }

    private fun getToolBar(): JComponent {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.X_AXIS)

        val group = DefaultActionGroup()
        group.add(AddTabAction())
        group.add(RemoveTabAction())

        val actionToolBar = ActionManager.getInstance().createActionToolbar("DemoTools", group, false)

        panel.add(actionToolBar.component)

        return panel
    }

    private fun getNextTabDisplayName(): String {
        return "Tab $TAB_COUNT"
    }

    inner class AddTabAction : AnAction("Add", "Add tab", Icons.Add) {
        override fun actionPerformed(e: AnActionEvent?) {
            TAB_COUNT += 1
            val contentFactory = ContentFactory.SERVICE.getInstance()
            val content = contentFactory.createContent(createToolWindowPanel(), getNextTabDisplayName(), false)
            toolwindow!!.contentManager.addContent(content)
            toolwindow!!.contentManager.setSelectedContent(content)
        }
    }

    inner class RemoveTabAction : AnAction("Remove", "Remove tab", Icons.Remove) {
        override fun actionPerformed(e: AnActionEvent?) {
            val count = toolwindow!!.contentManager.contentCount
            if (count > 1) {
                val content = toolwindow!!.contentManager.selectedContent!!
                toolwindow!!.contentManager.removeContent(content, true)
            } else {
                Messages.showMessageDialog("Must contains at least one tab", "Remove tab", Messages.getInformationIcon())
            }
        }
    }

    companion object {
        var TAB_COUNT = 1
    }
}