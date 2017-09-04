package com.spreadst.devtools.utils;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

// TODO: should generate this file automatically
public class Icons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, Icons.class);
    }

    public static final Icon MainEntry = load("/icons/main_entry.png"); // 16x16
}
