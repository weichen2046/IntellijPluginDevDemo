package com.spreadst.devtools.demos.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class DemoFileEditorProvider implements FileEditorProvider {

    private static final String ID = "st-demo-editor";

    private static final String sSuffix = DemoFileType.DOT_EXT_DEMO;

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
        String path = file.getPath();
        return path.regionMatches(true, path.length() - sSuffix.length(), sSuffix, 0, sSuffix.length());
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
        return new DemoViewEditor();
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
