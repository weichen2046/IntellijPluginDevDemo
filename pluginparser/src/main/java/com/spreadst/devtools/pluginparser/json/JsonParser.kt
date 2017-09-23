package com.spreadst.devtools.pluginparser.json

import com.google.gson.Gson
import com.jetbrains.plugin.structure.base.plugin.PluginCreationFail
import com.jetbrains.plugin.structure.base.plugin.PluginCreationResult
import com.jetbrains.plugin.structure.base.plugin.PluginCreationSuccess
import com.jetbrains.plugin.structure.base.plugin.PluginProblem
import com.jetbrains.plugin.structure.intellij.plugin.IdePlugin
import com.jetbrains.plugin.structure.intellij.plugin.IdePluginManager
import java.io.File

sealed class JsonResult(val status: Boolean)
data class JsonResultSuccess(val plugin: IdePlugin, val warnings: List<PluginProblem>? = null) : JsonResult(true)
data class JsonResultFail(val errorsAndWarnings: List<PluginProblem>? = null) : JsonResult(false)

/**
 * Parse plugin jar/zip file and generator json output.
 */
object JsonParser {

    fun parsePlugin(pluginFile: String, withErrorsOrWarnnings: Boolean = false): String {
        return parsePlugin(File(pluginFile), withErrorsOrWarnnings)
    }

    fun parsePlugin(pluginFile: File, withErrorsOrWarnnings: Boolean = false): String {
        val pluginResult: PluginCreationResult<IdePlugin>
                = IdePluginManager.createManager().createPlugin(pluginFile)
        val gson = Gson()
        return when (pluginResult) {
            is PluginCreationSuccess -> {
                val jsonR = JsonResultSuccess(pluginResult.plugin, if (withErrorsOrWarnnings) pluginResult.warnings else null)
                gson.toJson(jsonR)
            }
            is PluginCreationFail -> {
                val jsonR = JsonResultFail(if (withErrorsOrWarnnings) pluginResult.errorsAndWarnings else null)
                gson.toJson(jsonR)
            }
        }
    }
}