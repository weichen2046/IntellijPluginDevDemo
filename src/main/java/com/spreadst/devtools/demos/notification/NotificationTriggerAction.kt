package com.spreadst.devtools.demos.notification

import com.intellij.notification.*
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import icons.Icons
import javax.swing.event.HyperlinkEvent

class NotificationTriggerAction : AnAction("Demo Notification", "Trigger demo notification", Icons.DemoNotification) {
    override fun actionPerformed(e: AnActionEvent?) {
        // https://www.jetbrains.org/intellij/sdk/docs/user_interface_components/notifications.html

        val message = "Content\n<a href=\"https://github.com/weichen2046/IntellijPluginDevDemo\">Link</a>"

        // Custom notification group
        val notification = notificationGroup.createNotification("Title", "Sub Title", message, NotificationType.INFORMATION,
                { notification, event ->
                    run {
                        Messages.showMessageDialog("Link clicked ${event.url}", "Notification Link",
                                Messages.getInformationIcon())
                        notification.hideBalloon()
                    }
                })
        notification.addAction(object: AnAction("Action1", "Action Description", null) {
            override fun actionPerformed(e: AnActionEvent?) {
                notification.hideBalloon()
            }
        })

        notification.addAction(object: AnAction("Action2", "Action Description", null) {
            override fun actionPerformed(e: AnActionEvent?) {
                notification.hideBalloon()
            }
        })

        // Need register notification group display id
        val notification2 =
                Notification(Notifications.SYSTEM_MESSAGES_GROUP_ID,
                        Icons.DemoNotification,
                        "Title",
                        "Sub Title",
                        message,
                        NotificationType.INFORMATION,
                        { notification: Notification, event: HyperlinkEvent ->
                            run {
                                Messages.showMessageDialog("Link clicked ${event.url}", "Notification Link",
                                        Messages.getInformationIcon())
                                notification.hideBalloon()
                            }
                        })

        Notifications.Bus.notify(notification)
    }

    companion object {
        val notificationGroup = NotificationGroup("Demo notification group", NotificationDisplayType.BALLOON,
                true, null, Icons.DemoNotification)
    }
}