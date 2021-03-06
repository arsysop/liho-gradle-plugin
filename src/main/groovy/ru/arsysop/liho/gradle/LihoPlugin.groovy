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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * <p>
 *     Main entry point to the {@code liho} plugin. {@code Applying} of this plugin means that
 * </p>
 * <ul>
 *     <li><a href="https://docs.gradle.org/current/userguide/base_plugin.html"><i>base</i> plugin</a> is applied automatically</li>
 *     <li><i>liho {}</i> configurator block is added to  available project configurators</li>
 *     <li><i>liho</i> <i>verification</i> task is added to the project and </li>
 *     <li>this task is automatically bound to the <i>check</i> meta-task of <i>base</i> plugin</li>
 * </ul>
 *
 * @see LihoTask
 * @see Configuration
 * @since 0.1
 */
@SuppressWarnings("unused")
class LihoPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.apply([plugin: 'base'])
        project.extensions.create('liho', Configuration)
        Task liho = project.task([type: LihoTask], 'liho') {
            root.set(project.liho.root)
            strict.set(project.liho.strict)
            report.set(project.liho.report)
        }
        project.tasks.getByName("check").dependsOn(liho)
    }

}
