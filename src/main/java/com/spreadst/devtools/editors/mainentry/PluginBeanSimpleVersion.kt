package com.spreadst.devtools.editors.mainentry

/**
 * {
 *     "id":"com.spreadst.devtools.demoplugin",
 *     "name":"Demo Plugin"
 *     "version":"1.0",
 *     "url":"http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar",
 *     "icon":null,
 * }
 */
data class PluginBeanSimpleVersion(val id: String, val name: String, val version: String, val url: String, val icon: String?)
