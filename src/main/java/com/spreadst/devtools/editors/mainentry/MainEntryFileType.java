package com.spreadst.devtools.editors.mainentry;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import icons.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MainEntryFileType implements FileType {
    public static final MainEntryFileType INSTANCE = new MainEntryFileType();
    public static final String EXT_MAIN_ENTRY = "stme";
    public static final String DOT_EXT_MAIN_ENTRY = ".stme";

    @NotNull
    @Override
    public String getName() {
        return "Tools Main Entry File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Tools Main Entry File Type";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXT_MAIN_ENTRY;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.MainEntry;
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
