package uk.co.ceilingcat.gradle.plugins.versioning.tasks

import org.gradle.api.tasks.TaskAction

class MinorIncrement extends TaskBase {

    static final CharSequence NAME = '''minorIncrement'''
    static final CharSequence DESCRIPTION = '''Increments the minor version component.'''

    MinorIncrement() {
        super(DESCRIPTION)
    }

    @TaskAction
    void run() {
        setGradleVersion(write(read().minorIncrement()))
    }
}
