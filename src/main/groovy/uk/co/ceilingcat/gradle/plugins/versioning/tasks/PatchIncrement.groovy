package uk.co.ceilingcat.gradle.plugins.versioning.tasks

import org.gradle.api.tasks.TaskAction

class PatchIncrement extends TaskBase {

    static final CharSequence NAME = '''patchIncrement'''
    static final CharSequence DESCRIPTION = '''Increments the patch version component.'''

    PatchIncrement() {
        super(DESCRIPTION)
    }

    @TaskAction
    void run() {
        setGradleVersion(write(read().patchIncrement()))
    }
}
