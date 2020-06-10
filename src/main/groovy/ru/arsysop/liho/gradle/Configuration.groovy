package ru.arsysop.liho.gradle

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

import javax.inject.Inject

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