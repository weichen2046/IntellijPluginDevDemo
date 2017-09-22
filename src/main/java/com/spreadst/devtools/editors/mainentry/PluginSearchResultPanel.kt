package com.spreadst.devtools.editors.mainentry

import com.intellij.openapi.Disposable
import com.intellij.ui.components.labels.BoldLabel
import com.intellij.vcs.log.ui.frame.WrappedFlowLayout
import java.awt.*
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
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

        addComponentListener(object: ComponentListener {
            override fun componentMoved(e: ComponentEvent?) {
            }

            override fun componentResized(e: ComponentEvent?) {
                //(innerPanel.resultPanel.layout as WrappedFlowLayout).layoutContainer(innerPanel.resultPanel)
                innerPanel.readd()
            }

            override fun componentHidden(e: ComponentEvent?) {
            }

            override fun componentShown(e: ComponentEvent?) {
            }

        })
    }

    fun search(term: String) {
        innerPanel.refresh(term)
    }

    class InnerPanel(private val fetcher: PluginInfoFetcher) :
            JPanel(), Disposable, PluginInfoFetcher.PluginInfoFetcherListener {
        private var itemPrefSize: Dimension
        private var resultPanel: JPanel

        init {
            val fake = PluginItemView()
            fake.setTitle("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
            fake.setContent("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
            itemPrefSize = fake.preferredSize

            background = Color.YELLOW

            resultPanel = JPanel()
            val l = WrappedFlowLayout(40, 20)
            //val l = FlowLayout()
            //val l = GridLayout(0, 3)
            l.hgap = 40
            l.vgap = 20
            resultPanel.layout = l
            add(resultPanel, BorderLayout.NORTH)



            fetcher.addFetchListener(this)
        }

        fun refresh(term: String) {
            // TODO: send search request delay
            fetcher.refresh(term)
        }

        override fun dispose() {
            fetcher.removeFetchListener(this)
        }

        override fun onFinished(plugins: List<PluginBeanExt>) {
            resetAndAdd(plugins)
        }

        fun readd() {
            val items = resultPanel.components
            clear()
            for (item in items) {
                resultPanel.add(item)
            }
        }

        private fun resetAndAdd(items: Collection<PluginBeanExt>) {
            clear()
            addItems(items)
            resultPanel.revalidate()
        }

        private fun addItems(items: Collection<PluginBeanExt>) {
            for (item in items) {
                addItem(item)
            }
        }

        private fun addItem(item: PluginBeanExt) {
            val view = mapPluginBeanToItemView(item)
            resultPanel.add(view)
        }

        private fun clear() {
            resultPanel.removeAll()
        }

        private fun mapPluginBeanToItemView(plugin: PluginBeanExt): PluginItemView {
            val view = PluginItemView(plugin.actionsExt)
            view.setTitle(plugin.name)
            view.setContent(plugin.pluginVersion)
            view.preferredSize = itemPrefSize
            return view
        }
    }
}