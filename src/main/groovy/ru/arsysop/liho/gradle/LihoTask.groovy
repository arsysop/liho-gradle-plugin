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
import org.gradle.api.tasks.*
import ru.arsysop.liho.bulk.AnalyzedTree

class LihoTask extends DefaultTask {

    @InputDirectory
    final Property<File> root = project.objects.property(File)

    @Input
    final Property<Boolean> strict = project.objects.property(Boolean)

    @OutputFile
    final RegularFileProperty report = project.objects.fileProperty()

    LihoTask() {
        setGroup("verification")
        setDescription("Check if all the source files start with proper license headers.")
    }

    @TaskAction
    void troubleLiho() {
        report.get().asFile.with { file ->
            file.withOutputStream { stream ->
                analyseAndReport(file, stream)
            }
        }
        setDidWork(true)
    }

    // due to gradle runtime peculiarities, despite private access is compilable here, private method is not accessible at runtime
    void analyseAndReport(File file, OutputStream output) {
        VindictiveReport results = new VindictiveReport(file, output)
        new AnalyzedTree(results).accept(root.get().toPath())
        if (results.failed()) {
            if (strict.get()) {
                throw new TaskExecutionException(this, new LihoCheckFailureException(results))
            } else {
                System.err.println("WARNING: ${results.summary()}")
            }
        }
    }

}
