package com.spreadst.devtools.demos.popup

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import javax.swing.Icon

import icons.Icons

class OpenDemoPopupAction : AnAction("Demo Popup", "Open demo popup", null) {
    override fun actionPerformed(e: AnActionEvent?) {
        val title = "Popup Title"
        val values = getValues()
        val icons = getIcons()
        val popup = JBPopupFactory.getInstance().createListPopup(MyListPopupStep(title, values, icons))
        popup.showCenteredInCurrentWindow(e!!.project!!)
    }

    private fun getValues(): List<String> {
        val values = ArrayList<String>()
        (0 until 26).mapTo(values) { ('A'.toInt() + it).toChar().toString() }
        return values
    }

    private fun getIcons(): List<Icon> {
        val icons = ArrayList<Icon>()
        (0 until 26).mapTo(icons) { Icons.DemoEditor }
        return icons
    }

    /**
     * http://www.jetbrains.org/intellij/sdk/docs/user_interface_components/popups.html
     */
    inner class MyListPopupStep(title: String, values: List<String>, icons: List<Icon>)
        : BaseListPopupStep<String>(title, values, icons) {

        private var selectedVal: String? = null

        override fun onChosen(selectedValue: String?, finalChoice: Boolean): PopupStep<*>? {
            selectedVal = selectedValue
            return super.onChosen(selectedValue, finalChoice)
        }

        override fun getFinalRunnable(): Runnable? {
            return Runnable { Messages.showMessageDialog("You selected $selectedVal", "Selected Value", Messages.getInformationIcon()) }
        }
    }
}