package com.spreadst.devtools.demos.editor;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import icons.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DemoFileType implements FileType {
    public static final DemoFileType INSTANCE = new DemoFileType();
    public static final String EXT_DEMO = "demo";
    public static final String DOT_EXT_DEMO = ".demo";

    @NotNull
    @Override
    public String getName() {
        return "Demo File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Demo File Type";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXT_DEMO;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.DemoEditor;
    }

    @Override
    public boolean isBinary() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
        return null;
    }
}
