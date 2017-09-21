package com.spreadst.devtools.editors.mainentry

import com.intellij.ui.SeparatorComponent
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.labels.LinkLabel
import com.intellij.ui.components.panels.VerticalBox
import icons.Icons
import org.picocontainer.Disposable
import java.awt.*
import javax.swing.Box
import javax.swing.JLabel
import javax.swing.JPanel

class PluginListPanel(title: String, private var fetcher: PluginInfoFetcher)
    : JPanel(), PluginInfoFetcher.PluginInfoFetcherListener, Disposable {
    private var titleLabel = JLabel()
    private var innerList: InnerPanel

    init {
        layout = GridBagLayout()

        val titlePanel = JPanel(GridBagLayout())

        titleLabel.text = title
        titleLabel.horizontalAlignment = JLabel.CENTER
        titleLabel.font = titleLabel.font.deriveFont(titleLabel.font.style or Font.BOLD)

        val c0 = GridBagConstraints()
        c0.fill = GridBagConstraints.HORIZONTAL
        c0.gridy = 0

        c0.gridx = 0
        c0.weightx = 1.0
        titlePanel.add(titleLabel, c0)

        val refreshBtn = LinkLabel.create("", {
            refresh()
        })
        refreshBtn.icon = Icons.Refresh
        c0.weightx = 0.0
        c0.gridx = 1
        c0.insets.right = 10
        titlePanel.add(refreshBtn, c0)

        val c = GridBagConstraints()
        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        c.gridx = 0

        c.gridy = 0
        c.ipady = 20
        add(titlePanel, c)

        innerList = InnerPanel()
        val scrollPanel = JBScrollPane(innerList)
        c.gridy = 1
        c.ipady = 0
        c.weighty = 1.0
        add(scrollPanel, c)

        fetcher.addFetchListener(this)
        fetcher.refresh()
    }

    override fun onFinished(plugins: List<PluginBeanExt>) {
        innerList.resetAndAdd(plugins)
    }

    override fun dispose() {
        fetcher.removeFetchListener(this)
    }

    fun setTitle(title: String) {
        titleLabel.text = title
    }

    private fun refresh() {
        fetcher.refresh()
    }

    class InnerPanel : VerticalBox() {
        private val items = ArrayList<PluginBeanExt>()

        fun resetAndAdd(items: Collection<PluginBeanExt>) {
            clear()
            addItems(items)
            add(Box.createGlue())
        }

        fun addItems(items: Collection<PluginBeanExt>) {
            for (item in items) {
                addItem(item)
            }
        }

        fun addItem(item: PluginBeanExt) {
            if (items.size > 0) {
                add(SeparatorComponent())
            }
            items.add(item)
            val view = mapPluginBeanToItemView(item)
            add(view)
        }

        fun clear() {
            items.clear()
            removeAll()
        }

        private fun mapPluginBeanToItemView(plugin: PluginBeanExt): PluginItemView {
            val view = PluginItemView(plugin.actionsExt)
            view.maximumSize = Dimension(Int.MAX_VALUE, 400)
            view.setTitle(plugin.name)
            view.setContent(plugin.pluginVersion)
            return view
        }
    }
}