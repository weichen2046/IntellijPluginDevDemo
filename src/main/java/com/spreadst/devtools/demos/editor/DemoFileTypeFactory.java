package com.spreadst.devtools.demos.editor;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class DemoFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(DemoFileType.INSTANCE, DemoFileType.INSTANCE.getDefaultExtension());
    }
}
