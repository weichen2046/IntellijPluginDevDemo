package com.spreadst.devtools.editors.mainentry;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.DocumentAdapter;
import com.intellij.ui.components.JBTextField;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class MainEntryViewer implements Disposable {

    private static final String SEARCH_RESULT_PANEL = "SearchResultPanel";
    private static final String RECOMMENDATION_PANEL = "RecommendationPanel";

    private static final int MIN_SEARCH_STRING_LENGTH = 3;

    private JPanel mRootComponent;

    private JBTextField mSearchBox;
    private JPanel mBottomPanel;
    private PluginSearchResultPanel mSearchResultContainer;
    private JPanel mRecommendationPanel;

    public MainEntryViewer(Disposable parent) {
        mRootComponent = new JPanel();
        initContainerComponent(mRootComponent);
        initSearchBox();
        Disposer.register(parent, this);
    }

    @Override
    public void dispose() {
        // TODO: release resources
    }

    public JComponent getRootComponent() {
        return mRootComponent;
    }

    private void initContainerComponent(@Nonnull JComponent container) {
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridx = 0;

        c.gridy = 0;
        c.insets.set(100, 0, 100, 0);
        container.add(getSearchPanel(), c);

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        c.gridy = 1;
        c.insets.set(0, 0, 0, 0);
        container.add(getBottomPanel(), c);
    }

    private JPanel getSearchPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;

        c.gridx = 0;
        c.weightx = 0.15;
        JLabel label = new JLabel("Search: ");
        label.setHorizontalAlignment(JLabel.RIGHT);
        Font f = label.getFont();
        label.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        panel.add(label, c);

        c.gridx = 1;
        c.weightx = 0.7;
        mSearchBox = new JBTextField();
        panel.add(mSearchBox, c);

        c.gridx = 2;
        c.weightx = 0.15;
        panel.add(new JLabel(), c);

        return panel;
    }

    private JPanel getBottomPanel() {
        mBottomPanel = new JPanel(new CardLayout());
        mBottomPanel.add(getRecommendationPanel(), RECOMMENDATION_PANEL);
        mBottomPanel.add(getSearchResultContainer(), SEARCH_RESULT_PANEL);
        return mBottomPanel;
    }

    private JPanel getRecommendationPanel() {
        mRecommendationPanel = new JPanel();
        mRecommendationPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridy = 0;
        c.insets.left = 40;

        c.gridx = 0;
        mRecommendationPanel.add(getHotPluginPanel(), c);

        c.insets.left = 20;
        c.gridx = 1;
        mRecommendationPanel.add(getRecentNewPluginPanel(), c);

        c.gridx = 2;
        c.insets.right = 40;
        mRecommendationPanel.add(getUpdateAvailablePanel(), c);
        return mRecommendationPanel;
    }

    private JPanel getHotPluginPanel() {
        PluginInfoFetcher fetcher = new PluginInfoFetcher("", PluginInfoFetcher.FetcherType.HOT);
        JPanel hot = new PluginListPanel("Hot Plugins", fetcher);
        return hot;
    }

    private JPanel getRecentNewPluginPanel() {
        PluginInfoFetcher fetcher = new PluginInfoFetcher("", PluginInfoFetcher.FetcherType.NEW_ADDED);
        JPanel recent = new PluginListPanel("Recent Added Plugins", fetcher);
        return recent;
    }

    private JPanel getUpdateAvailablePanel() {
        PluginInfoFetcher fetcher = new PluginInfoFetcher("", PluginInfoFetcher.FetcherType.UPDATE_AVAILABLE);
        JPanel update = new PluginListPanel("Update Available Plugins", fetcher);
        return update;
    }

    private JPanel getSearchResultContainer() {
        mSearchResultContainer = new PluginSearchResultPanel();
        return mSearchResultContainer;
    }

    private void initSearchBox() {
        mSearchBox.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void textChanged(DocumentEvent e) {
                int termLength = e.getDocument().getLength();
                if (termLength >= MIN_SEARCH_STRING_LENGTH) {
                    ((CardLayout) mBottomPanel.getLayout()).show(mBottomPanel, SEARCH_RESULT_PANEL);
                    try {
                        mSearchResultContainer.search(e.getDocument().getText(0, termLength));
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                } else if (termLength == 0) {
                    ((CardLayout) mBottomPanel.getLayout()).show(mBottomPanel, RECOMMENDATION_PANEL);
                }
            }
        });
    }


}
