package com.spreadst.devtools.editors.mainentry

import com.intellij.ide.plugins.PluginBean

class PluginBeanExt : PluginBean() {
    var iconUrl: String? = null
    val actionsExt = ArrayList<Pair<String, Runnable>>()

    companion object {
        fun fromSimpleVersion(simpleObj: PluginBeanSimpleVersion): PluginBeanExt {
            val plugin = PluginBeanExt()
            plugin.id = simpleObj.id
            plugin.name = simpleObj.name
            plugin.pluginVersion = simpleObj.version
            plugin.url = simpleObj.url
            plugin.iconUrl = simpleObj.icon
            return plugin
        }
    }
}