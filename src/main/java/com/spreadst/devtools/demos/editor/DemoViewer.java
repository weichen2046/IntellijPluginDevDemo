package com.spreadst.devtools.demos.editor;

import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoViewer {
    public JComponent getRootComponent() {
        JButton btn = new JButton("Test Button");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Messages.showMessageDialog("Demo Editor Message", "Demo Editor", Messages.getInformationIcon());
            }
        });
        return btn;
    }
}
