package com.spreadst.devtools.android.editors.mainentry;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainEntryViewer {
    public JComponent getRootComponent() {
        JButton btn = new JButton("Test Button");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Messages.showMessageDialog("Main Entry Demo Editor", "Main Entry", Messages.getInformationIcon());
            }
        });
        return btn;
    }
}
