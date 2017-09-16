package com.spreadst.devtools.editors.mainentry;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.spreadst.devtools.demos.filesystem.DummyFileSystem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainEntryVirtualFile extends VirtualFile {
    private static final String NAME = "Main Entry";
    private static final String PATH = "Main Entry.stme";

    public static final MainEntryVirtualFile INSTANCE = new MainEntryVirtualFile();

    private static final DummyFileSystem mOutFileSystem = new DummyFileSystem();

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public VirtualFileSystem getFileSystem() {
        return mOutFileSystem;
    }

    @NotNull
    @Override
    public FileType getFileType() {
        // This will give us the correct icon in editor tab even though file
        // type can not infer from file name.
        return MainEntryFileType.INSTANCE;
    }

    @NotNull
    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public boolean isWritable() {
        // Returns true to remove the lock icon from the editor tab.
        return true;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public VirtualFile getParent() {
        return null;
    }

    @Override
    public VirtualFile[] getChildren() {
        return null;
    }

    @NotNull
    @Override
    public OutputStream getOutputStream(Object requestor, long newModificationStamp, long newTimeStamp) throws IOException {
        return null;
    }

    @NotNull
    @Override
    public byte[] contentsToByteArray() throws IOException {
        return new byte[0];
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public void refresh(boolean asynchronous, boolean recursive, @Nullable Runnable postRunnable) {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public long getModificationStamp() {
        return 0L;
    }
}
