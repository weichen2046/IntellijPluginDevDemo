# IntellijPluginDevDemo

Intellij IDEA plugin development demos.

## Current Supports

- [Action Demo](src/com/spreadst/devtools/demos/action/MyActionClass.java)

  ```
  <actions>
    <!-- Add your actions here -->
    <group id="MyPlugin.SampleMenu" text="_Sample Menu" description="Sample menu">
      <add-to-group group-id="MainMenu" anchor="last"  />
      <!-- icon of action should be placed in a top-level package called icons -->
      <action id="MyPlugin.MyAction" class="com.spreadst.devtools.demos.action.MyActionClass" text="My_Item"
              description="Description of the action item" icon="Icons.AndroidRobot"/>
      <action id="MyPlugin.OpenMainEntry" class="com.spreadst.devtools.android.editors.mainentry.OpenMainEntryAction"
              text="_Main Entry" description="Open tools main entry" icon="Icons.MainEntry"/>
    </group>
  </actions>
  ```

  ![snapshot](snapshots/plugin_action_demo.png)

- [Custom Editor Demo](src/com/spreadst/devtools/android/editors/mainentry/)
