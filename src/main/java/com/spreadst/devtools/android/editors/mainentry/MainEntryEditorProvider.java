package com.spreadst.devtools.android.editors.mainentry;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class MainEntryEditorProvider implements FileEditorProvider {

    private static final String ID = "st-main-entry-editor";

    private static final String sSuffix = MainEntryFileType.DOT_EXT_MAIN_ENTRY;

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        String path = file.getPath();
        return path.regionMatches(true, path.length() - sSuffix.length(), sSuffix, 0, sSuffix.length());
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        return new MainEntryViewEditor();
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return ID;
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
