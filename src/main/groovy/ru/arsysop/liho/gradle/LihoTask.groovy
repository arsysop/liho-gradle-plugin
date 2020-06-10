/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package ru.arsysop.liho.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import ru.arsysop.liho.bulk.AnalyzedTree
import ru.arsysop.liho.report.Report
import ru.arsysop.liho.report.StreamingReport

class LihoTask extends DefaultTask {

    @InputDirectory
    final Property<File> root = project.objects.property(File)

    @Input
    final Property<Boolean> strict = project.objects.property(Boolean)

    @OutputFile
    final RegularFileProperty report = project.objects.fileProperty()

    LihoTask() {
        setGroup("validation")
        setDescription("Check if all the source files start with proper license headers")
    }

    @TaskAction
    void troubleLiho() {
        File file = report.get().asFile
        logger.info("Liho is about to analyse ${root.get().getAbsolutePath()} and store analysis results in ${file.getAbsolutePath()}")
        file.withOutputStream { output ->
            Report report = new StreamingReport(new PrintStream(output))
            new AnalyzedTree(report).accept(root.get().toPath())
        }
    }

}
