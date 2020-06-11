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

import groovy.transform.PackageScope
import ru.arsysop.liho.report.IssueLocation
import ru.arsysop.liho.report.IssueType
import ru.arsysop.liho.report.Report
import ru.arsysop.liho.report.StreamingReport

import java.util.concurrent.atomic.AtomicInteger

@PackageScope
class VindictiveReport implements Report {

    private final StreamingReport delegate
    private final File file
    private final AtomicInteger counter = new AtomicInteger(0)

    VindictiveReport(File file, OutputStream output) {
        this.file = file
        this.delegate = new StreamingReport(new PrintStream(output))
    }

    @Override
    void issue(IssueType type, IssueLocation location) {
        counter.incrementAndGet()
        delegate.issue(type, location)
    }

    int issues() {
        counter.get()
    }

    boolean failed() {
        issues() > 0
    }

    String summary() {
        "LiHo found ${issues()} license header issues. Have a closer look at the report ${file.getAbsolutePath()}"
    }

}
