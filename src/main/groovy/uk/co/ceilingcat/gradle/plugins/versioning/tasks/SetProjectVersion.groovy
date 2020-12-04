package uk.co.ceilingcat.gradle.plugins.versioning.tasks

import org.gradle.api.tasks.TaskAction

class SetProjectVersion extends TaskBase {

    static final CharSequence NAME = '''setProjectVersion'''
    static final CharSequence DESCRIPTION = '''Sets the build's version property.'''

    SetProjectVersion() {
        super(DESCRIPTION)
    }

    @TaskAction
    void run() {
        setGradleVersion(read())
    }
}
