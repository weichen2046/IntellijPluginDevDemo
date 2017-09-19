package com.spreadst.devtools.demos.dialog

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.DialogBuilder
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.Messages
import javax.swing.JButton
import javax.swing.JComponent

class OpenDemoDialogAction : AnAction("Demo Dialog", "Open demo dialog", null) {
    override fun actionPerformed(e: AnActionEvent?) {
        val builder = DialogBuilder(e!!.project)
        builder.centerPanel(getCenterPanel())
        builder.title("Demo Dialog Title")
        builder.removeAllActions()
        builder.addOkAction()
        builder.addCancelAction()
        val result = builder.show()
        when (result) {
            DialogWrapper.OK_EXIT_CODE -> {
                Messages.showMessageDialog("Ok clicked", "Dialog Close Result", Messages.getInformationIcon())
            }
            DialogWrapper.CANCEL_EXIT_CODE -> {
                Messages.showMessageDialog("Cancel clicked", "Dialog Close Result", Messages.getInformationIcon())
            }
            else -> {
                Messages.showMessageDialog("Unknown button clicked", "Dialog Close Result", Messages.getInformationIcon())
            }
        }
    }

    private fun getCenterPanel(): JComponent {
        return JButton("Dialog Center Panel")
    }
}