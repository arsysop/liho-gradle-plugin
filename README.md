# liho-gradle-plugin

[![Build](https://github.com/arsysop/liho-gradle-plugin/workflows/Java%20CI/badge.svg)](https://github.com/arsysop/liho-gradle-plugin/actions?query=workflow%3A%22Java+CI%22)
[![Hits-of-Code](https://hitsofcode.com/github/arsysop/liho-gradle-plugin)](https://hitsofcode.com/view/github/arsysop/liho-gradle-plugin)

[![](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg)](https://github.com/arsysop/liho-gradle-plugin/blob/master/LICENSE)

[Standalone Plug-in](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:custom_plugins_standalone_project) 
for [Gradle](http://gradle.org), who's work is to employ 
[LiHo](https://github.com/arsysop/liho) checker to verify 
if a project source base is sufficiently equipped with license headers.  

**Currently is under construction.**

#### to publish
```
./gradlew publish
```
It builds and stores publish-ready artifacts into _buildDir/local-repo_.
 
#### to apply
1. _apply_ the plugin in _plugins_ block of your `build.gradle`
    ```groovy
    plugins {
        id("ru.arsysop.liho.liho-gradle-plugin") version "0.1"
    }
    ```
   As the plugin is published without [plugin marker](https://docs.gradle.org/current/userguide/plugins.html#sec:plugin_markers),
   configure _resolution strategy_ in your `settings.gradle` script:
   ```groovy
    pluginManagement {
        repositories {
            jcenter()
        }
        resolutionStrategy {
            eachPlugin {
                if (requested.id.namespace == "ru.arsysop.liho" &&
                    requested.id.name == "liho-gradle-plugin"
                ) {
                    useModule("ru.arsysop.liho:liho-gradle-plugin:${requested.version}")
                }
            }
        }
    }
   ```
2. configure plugin extension in your `build.gradle`:
    ```groovy
    liho {
        root.set(project.file("src"))
        strict.set(true)
        report.set(project.file("$buildDir/liho/report.txt"))
    }
    ```
3. run `liho` task:
    ```
    ./gradlew liho
    ```
   It analyses all the supported types of sources and stores report 
   into the configured _liho/report_ file
