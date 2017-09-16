package com.spreadst.devtools.demos.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class DemoViewEditor extends UserDataHolderBase implements FileEditor {

    private DemoViewer mMyViewer;

    public DemoViewEditor() {
        mMyViewer = new DemoViewer();
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return mMyViewer.getRootComponent();
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return "Demo Editor";
    }

    @Override
    public void setState(@NotNull FileEditorState state) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {

    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {

    }
}
