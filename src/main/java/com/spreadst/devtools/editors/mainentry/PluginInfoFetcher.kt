package com.spreadst.devtools.editors.mainentry

import com.google.gson.Gson
import com.intellij.util.concurrency.SwingWorker
import com.intellij.util.io.HttpRequests

class PluginInfoFetcher(private var url: String, private var type: FetcherType) {

    private val listeners = ArrayList<PluginInfoFetcherListener>()
    private var worker: SwingWorker? = null

    interface PluginInfoFetcherListener {
        fun onFinished(plugins: List<PluginBeanExt>)
    }

    enum class FetcherType {
        HOT,
        NEW_ADDED,
        UPDATE_AVAILABLE,
        SEARCH,
    }

    fun refresh(term: String? = null) {
        if (worker != null) {
            worker!!.interrupt()
        }
        worker = object : SwingWorker() {
            override fun construct(): Any {
                return doWorkInBackground(term)
            }

            override fun finished() {
                val v = get()
                if (v != null) {
                    val finalV = v as List<PluginBeanExt>
                    for (listener in listeners) {
                        listener.onFinished(finalV)
                    }
                }
                worker = null
            }
        }
        (worker as SwingWorker).start()
    }

    fun addFetchListener(listener: PluginInfoFetcherListener) {
        val index = listeners.indexOf(listener)
        if (index == -1) {
            listeners.add(listener)
        } else {
            listeners[index] = listener
        }
    }

    fun removeFetchListener(listener: PluginInfoFetcherListener): Boolean {
        return listeners.remove(listener)
    }

    fun doWorkInBackground(term: String?): List<PluginBeanExt> {
        when (type) {
            FetcherType.NEW_ADDED -> {
                return handleQueryNewAdded()
            }
            FetcherType.HOT -> {
                return handleQueryHot()
            }
            FetcherType.UPDATE_AVAILABLE -> {
                return handleQueryUpdateAvailable()
            }
            FetcherType.SEARCH -> {
                return handleQuerySearch(term!!)
            }
            else -> {
                return handleQueryForDebug()
            }
        }
    }

    private fun handleQueryNewAdded(): List<PluginBeanExt> {
        val plugins = ArrayList<PluginBeanExt>()
        // TODO: dynamic compose url
        val str = HttpRequests.request("http://127.0.0.1:8000/api/v1/plugin/query/?format=json&type=new_added&max=$DEFAULT_FETCH_RESULT_COUNT")
                .connect { request ->
                    request.readString(null)
                }
        // convert json response to plugins
        val result = Gson().fromJson(str, PluginFetchResult::class.java)
        when (result.status) {
            PluginFetchResult.STATUS_OK -> {
                result.plugins.mapTo(plugins, { it ->
                    val pluginExt = PluginBeanExt.fromSimpleVersion(it)
                    bindActionToPlugin(pluginExt)
                })
            }
        }
        return plugins
    }

    private fun handleQueryHot(): List<PluginBeanExt> {
        // TODO: fetch plugins from remote server
        return handleQueryForDebug()
    }

    private fun handleQueryUpdateAvailable(): List<PluginBeanExt> {
        // TODO: fetch plugins from remote server
        return handleQueryForDebug()
    }

    private fun handleQuerySearch(term: String): List<PluginBeanExt> {
        // TODO: fetch plugins from remote server
        return handleQueryForDebug()
    }

    private fun handleQueryForDebug(): List<PluginBeanExt> {
        val plugins = ArrayList<PluginBeanExt>()
        var plugin = PluginBeanExt()
        plugin.name = "Demo Plugin"
        plugin.pluginVersion = "1.0.1"
        plugin.iconUrl = "http://path/to/plugin/icon"
        plugins.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 2"
        plugin.pluginVersion = "1.0.2"
        plugin.iconUrl = "http://path/to/plugin/icon"
        plugins.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 3"
        plugin.pluginVersion = "1.0.3"
        plugin.iconUrl = "http://path/to/plugin/icon"
        plugins.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 4"
        plugin.pluginVersion = "1.0.4"
        plugin.iconUrl = "http://path/to/plugin/icon"
        plugins.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 5"
        plugin.pluginVersion = "1.0.5"
        plugin.iconUrl = "http://path/to/plugin/icon"
        plugins.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 6"
        plugin.pluginVersion = "1.0.6"
        plugin.iconUrl = "http://path/to/plugin/icon"
        plugins.add(plugin)
        bindActionToPlugin(plugin)
        return plugins
    }

    private fun bindActionToPlugin(plugin: PluginBeanExt): PluginBeanExt {
        when (type) {
            FetcherType.HOT, FetcherType.NEW_ADDED, FetcherType.SEARCH -> {
                plugin.actionsExt.add(Pair("Install", Runnable {
                    // TODO: install the requested plugin
                }))
                plugin.actionsExt.add(Pair("Details", Runnable {
                    // TODO: show the requested plugin details
                }))
            }
            FetcherType.UPDATE_AVAILABLE -> {
                plugin.actionsExt.add(Pair("Update", Runnable {
                    // TODO: update the requested plugin
                }))
                plugin.actionsExt.add(Pair("Details", Runnable {
                    // TODO: show the requested plugin details
                }))
            }
        }
        return plugin
    }

    companion object {
        const val DEFAULT_FETCH_RESULT_COUNT = 12
    }
}