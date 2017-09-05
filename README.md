# IntellijPluginDevDemo

Intellij IDEA plugin development demos.

## Current Supports

- [Action Demo](src/com/spreadst/devtools/demos/action/MyActionClass.java)

  ```
  <actions>
    <!-- Add your actions here -->
    <group id="MyPlugin.SampleMenu" text="_Sample Menu" description="Sample menu">
      <add-to-group group-id="MainMenu" anchor="last"  />
      <action id="MyPlugin.MyAction" class="com.spreadst.devtools.demos.action.MyActionClass" text="My_Item" description="Description of the action item" />
    </group>
  </actions>
  ```

  ![snapshot](snapshots/plugin_action_demo.png)

- [Custom Editor Demo](src/com/spreadst/devtools/android/editors/mainentry/)
