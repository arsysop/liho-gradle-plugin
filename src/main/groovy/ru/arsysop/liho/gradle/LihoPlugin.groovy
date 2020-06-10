package ru.arsysop.liho.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.arsysop.liho.bulk.AnalyzedTree
import ru.arsysop.liho.report.StreamingReport

class LihoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "apply LIHO plugin to ${project.name}"

        Configuration config = project.extensions.create('liho', Configuration)
        project.task('liho') {
            doLast {
                println "run Liho task for ${config.root}"
                new AnalyzedTree(new StreamingReport(System.out)).accept(config.root.toPath())
            }
        }
    }
}
