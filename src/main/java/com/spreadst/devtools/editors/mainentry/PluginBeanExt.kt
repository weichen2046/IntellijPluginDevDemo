package com.spreadst.devtools.editors.mainentry

import com.intellij.ide.plugins.PluginBean

class PluginBeanExt : PluginBean() {
    var iconUrl: String = ""
    val actionsExt = ArrayList<Pair<String, Runnable>>()
}