package com.spreadst.devtools.demos.filesystem;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileListener;
import com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class DummyFileSystem extends VirtualFileSystem {
    private static final String PROTOCOL = "dummy-fs";
    @NotNull
    @Override
    public String getProtocol() {
        return PROTOCOL;
    }

    @Nullable
    @Override
    public VirtualFile findFileByPath(@NotNull String path) {
        return null;
    }

    @Override
    public void refresh(boolean asynchronous) {

    }

    @Nullable
    @Override
    public VirtualFile refreshAndFindFileByPath(@NotNull String path) {
        return null;
    }

    @Override
    public void addVirtualFileListener(@NotNull VirtualFileListener listener) {

    }

    @Override
    public void removeVirtualFileListener(@NotNull VirtualFileListener listener) {

    }

    @Override
    protected void deleteFile(Object requestor, @NotNull VirtualFile vFile) throws IOException {

    }

    @Override
    protected void moveFile(Object requestor, @NotNull VirtualFile vFile, @NotNull VirtualFile newParent) throws IOException {

    }

    @Override
    protected void renameFile(Object requestor, @NotNull VirtualFile vFile, @NotNull String newName) throws IOException {

    }

    @NotNull
    @Override
    protected VirtualFile createChildFile(Object requestor, @NotNull VirtualFile vDir, @NotNull String fileName) throws IOException {
        return null;
    }

    @NotNull
    @Override
    protected VirtualFile createChildDirectory(Object requestor, @NotNull VirtualFile vDir, @NotNull String dirName) throws IOException {
        return null;
    }

    @NotNull
    @Override
    protected VirtualFile copyFile(Object requestor, @NotNull VirtualFile virtualFile, @NotNull VirtualFile newParent, @NotNull String copyName) throws IOException {
        return null;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }
}
