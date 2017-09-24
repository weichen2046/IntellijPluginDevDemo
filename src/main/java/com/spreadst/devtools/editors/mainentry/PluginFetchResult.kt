package com.spreadst.devtools.editors.mainentry

/**
 * {
 *     "status":"Ok",
 *     "plugins":[
 *         {
 *             "url":"http://127.0.0.1:8000/pluginjar/mobi.hsz.idea.gitignore/2.2.1/idea-gitignore-2.2.1.jar",
 *             "version":"2.2.1",
 *             "id":"mobi.hsz.idea.gitignore",
 *             "icon":null,
 *             "name":".ignore"
 *         },
 *         {
 *             "url":"http://127.0.0.1:8000/pluginjar/com.spreadst.devtools.demoplugin/1.0/Demo+Plugin-1.0.jar",
 *             "version":"1.0",
 *             "id":"com.spreadst.devtools.demoplugin",
 *             "icon":null,
 *             "name":"Demo Plugin"
 *         }
 *     ]
 * }
 */
data class PluginFetchResult(val status: String, val plugins: ArrayList<PluginBeanSimpleVersion>) {
    companion object {
        const val STATUS_OK = "Ok"
        const val STATUS_FAIL = "Fail"
    }
}