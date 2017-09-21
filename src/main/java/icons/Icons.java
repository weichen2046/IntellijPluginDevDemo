package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

// TODO: should generate this file automatically
public class Icons {
    private static Icon load(String path) {
        return IconLoader.getIcon(path, Icons.class);
    }

    public static final Icon MainEntry = load("/icons/main_entry.png"); // 16x16
    public static final Icon AndroidRobot = load("/icons/android_robot.png"); // 16x16
    public static final Icon DemoEditor = load("/icons/demo_editor.png"); // 16x16
    public static final Icon DemoToolWindow = load("/icons/demo_toolwindow.png"); // 13x13
    public static final Icon DemoNotification = load("/icons/demo_notifications.png"); // 16x16
    public static final Icon Add = load("/icons/add.png"); // 16x16
    public static final Icon Remove = load("/icons/remove.png"); // 16x16
    public static final Icon Refresh = load("/icons/refresh.png"); // 16x16
    public static final Icon CE_64 = load("/icons/icon_CE_64.png"); // 64x64
    public static final Icon CE_512 = load("/icons/icon_CE_512.png"); // 512x512
}
