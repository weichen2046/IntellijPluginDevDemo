package com.spreadst.devtools.editors.mainentry

import com.intellij.ui.components.labels.BoldLabel
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class PluginSearchResultPanel : JPanel() {
    private val title = BoldLabel("Search Result:")
    private val innerPanel = InnerPanel(PluginInfoFetcher("", PluginInfoFetcher.FetcherType.SEARCH))

    init {
        layout = BorderLayout()
        title.border = EmptyBorder(10, 40, 10, 0)
        add(title, BorderLayout.PAGE_START)
        add(innerPanel, BorderLayout.CENTER)
    }

    fun search(term: String) {
        innerPanel.refresh(term)
    }

    class InnerPanel(private val fetcher: PluginInfoFetcher) : JPanel(), PluginInfoFetcher.PluginInfoFetcherListener {
        init {
            background = Color.YELLOW

            // TODO: init inner panel
        }

        fun refresh(term: String) {
            // TODO: send search request delay
            //fetcher.refresh(term)
        }

        override fun onFinished(plugins: List<PluginBeanExt>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}