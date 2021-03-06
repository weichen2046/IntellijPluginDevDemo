Import project and build
========================

# Import project

## clone the repository

`git clone https://github.com/weichen2046/IntellijPluginDevDemo.git`

## Import as gradle project

  - Open Intellij IDEA and choose import project

    ![import gradle project](snapshots/build_project/import_gradle_project.png)

  - Config import settings as following picture

    ![config import](snapshots/build_project/config_gradle_import_settings.png)

    > - `Gradle project` is the path to your project root directory
    > - `Gradle JVM` should be your JAVA_HOME path
    > - Overrite exist `.idea` file if needed
    >
    >   ![overrite exist file](snapshots/build_project/overwrite_exist_file.png)

  - Remove not imported module

    ![remove not imported module](snapshots/build_project/remove_not_imported_module.png)

    > The name of the module to be removed will be different according to your project directory name.

  - After import successfully, module hierarchy like this picture:

    ![module hierarchy after import](snapshots/build_project/after_import_successfully.png)

  - Restore files after import

    Some file in vcs changes after import:

    ![file changes after import](snapshots/build_project/file_changes_after_import.png)

    Just restore them use command `git checkout .`.

  You have a clean project now.

# Build plugin

Open the Gradle tool window, you will get tasks to build and run plugins.

![gradle tasks](snapshots/build_project/build_plugins.png)

   - Run `build` or `buildPlugin` task to build plugin
   - Run `runIde` task to start sandbox IDEA to test and debug plugin