package com.spreadst.devtools.editors.mainentry;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;

public class OpenMainEntryAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // Open Main Entry Editor
        // Single instance of MainEntryVirtualFile will give us single editor
        // for it, that means if no editor opened for this virtual file, a new
        // editor will open, but if there already exists an editor for it, this
        // editor will get focused.
        VirtualFile vf = MainEntryVirtualFile.INSTANCE;
        FileEditorManager.getInstance(e.getProject()).openFile(vf, true);
    }
}
