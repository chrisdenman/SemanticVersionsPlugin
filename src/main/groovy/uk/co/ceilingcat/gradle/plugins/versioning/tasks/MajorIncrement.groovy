package uk.co.ceilingcat.gradle.plugins.versioning.tasks

import org.gradle.api.tasks.TaskAction

class MajorIncrement extends TaskBase {

    static final CharSequence NAME = '''majorIncrement'''
    static final CharSequence DESCRIPTION = '''Increments the major version component.'''

    MajorIncrement() {
        super(DESCRIPTION)
    }

    @TaskAction
    void run() {
        setGradleVersion(write(read().majorIncrement()))
    }
}
