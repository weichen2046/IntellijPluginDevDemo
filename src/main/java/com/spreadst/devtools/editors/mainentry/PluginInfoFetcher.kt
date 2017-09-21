package com.spreadst.devtools.editors.mainentry

import com.intellij.util.concurrency.SwingWorker

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
        worker = object: SwingWorker() {
            override fun construct(): Any {
                return doWorkInBackground(term)
            }

            override fun finished() {
                for (listener in listeners) {
                    listener.onFinished(worker!!.get() as List<PluginBeanExt>)
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
        val pluigns = ArrayList<PluginBeanExt>()

        // TODO: fetch plugins from remote server

        var plugin = PluginBeanExt()
        plugin.name = "Demo Plugin"
        plugin.pluginVersion = "1.0.1"
        plugin.iconUrl = "http://path/to/plugin/icon"
        pluigns.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 2"
        plugin.pluginVersion = "1.0.2"
        plugin.iconUrl = "http://path/to/plugin/icon"
        pluigns.add(plugin)
        bindActionToPlugin(plugin)

        plugin = PluginBeanExt()
        plugin.name = "Demo Plugin 3"
        plugin.pluginVersion = "1.0.3"
        plugin.iconUrl = "http://path/to/plugin/icon"
        pluigns.add(plugin)
        bindActionToPlugin(plugin)

        return pluigns
    }

    private fun bindActionToPlugin(plugin: PluginBeanExt) {
        when (type) {
            FetcherType.HOT, FetcherType.NEW_ADDED -> {
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
    }
}