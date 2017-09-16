package com.spreadst.devtools.editors.mainentry;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class MainEntryFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(MainEntryFileType.INSTANCE, MainEntryFileType.INSTANCE.getDefaultExtension());
    }
}
