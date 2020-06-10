package ru.arsysop.liho.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

@SuppressWarnings("unused")
class LihoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('liho', Configuration)
        project.task([type: LihoTask], 'liho') {
            root.set(project.liho.root)
            strict.set(project.liho.strict)
            report.set(project.liho.report)
        }
    }

}