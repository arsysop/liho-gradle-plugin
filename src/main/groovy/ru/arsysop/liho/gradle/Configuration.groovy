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

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

import javax.inject.Inject

/**
 * <p>Plugin <a href="https://docs.gradle.org/current/userguide/custom_plugins.html#sec:getting_input_from_the_build">extension</a>
 * allows to configure the checker behaviour, input source and output report.</p>
 *
 * <p>Properties to configure:</p>
 * <ul>
 *     <li><i>strict</i> prescribes to fail a build if any license header issue is detected.
 *     Textual warning is be printed on a not-<i>strict</i> run;</li>
 *     <li><i>root</i> points to a directory starting from which source base is to be analyzed;</li>
 *     <li><i>report</i> should point to a file for the final analysis report.</li>
 * </ul>
 *
 * <p>To use it in your build script, add the following block configured for your needs:</p>
 * <pre>
 * liho {
 *        root.set(project.file("src"))
 *        strict.set(true)
 *        report.set(project.file("$buildDir/liho/report.txt"))
 *      }
 * </pre>
 *
 * @since 0.1
 */
class Configuration {

    final Property<File> root
    final Property<Boolean> strict
    final RegularFileProperty report

    @Inject
    Configuration(ObjectFactory objects) {
        root = objects.property(File)
        strict = objects.property(Boolean)
        report = objects.fileProperty()
    }

}
