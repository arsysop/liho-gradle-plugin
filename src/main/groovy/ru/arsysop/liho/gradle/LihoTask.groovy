/* *****************************************************************************
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

/**
 * <p>Task of <i>verification</i> group, which is supplied to a project by this plugin. It </p>
 * <ul>
 * <li>runs  license headers checking across all the sources configured with <i>liho { root }</i>
 * <li>stores descriptions of all issues found to the configured <i>report</i> file, and</li>
 * <li>finishes with success, if all the license headers are ok or, otherwise, notifies the embracing build:
 * fails if it's configured as {@code strict} and print warning message, if it's not.</li>
 * </ul>
 *
 * <p>To call directly, use </p>
 * <pre>./gradlew liho</pre>
 *
 * <p>Task is <a href="https://docs.gradle.org/current/userguide/lazy_configuration.html">lazy</a>, which means it keeps
 * <i>up-to-date</i> state while neither of configured source base, output report or this plugin binary is changed.</p>
 *
 * <p>Task is attached to <a href="https://docs.gradle.org/current/userguide/base_plugin.html">{@code 'base'} plugin</a>'s
 * {@code check} meta-task, thus it's going to run on any call of {@code gradlew check}.
 * </p>
 * <p>To configure this task behaviour, use <i>liho {}</i> configuration block</p>
 *
 * @see Configuration
 *
 * @since 0.1
 */
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
