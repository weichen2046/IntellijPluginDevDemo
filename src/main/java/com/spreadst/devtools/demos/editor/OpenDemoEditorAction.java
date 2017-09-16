package com.spreadst.devtools.demos.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;

public class OpenDemoEditorAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // Open Demo Editor
        // Single instance of DemoVirtualFile will give us single editor
        // for it, that means if no editor opened for this virtual file, a new
        // editor will open, but if there already exists an editor for it, this
        // editor will get focused.
        VirtualFile vf = DemoVirtualFile.INSTANCE;
        FileEditorManager.getInstance(e.getProject()).openFile(vf, true);
    }
}
