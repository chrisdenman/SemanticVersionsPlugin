package uk.co.ceilingcat.gradle.plugins.versioning.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import uk.co.ceilingcat.gradle.plugins.versioning.SemanticVersionsPlugin
import uk.co.ceilingcat.gradle.plugins.versioning.Versions

abstract class TaskBase extends DefaultTask {

    static final CharSequence GROUP = 'MetaData'

    protected Property<CharSequence> propertiesPath = project.objects.property(CharSequence)

    protected SemanticVersionsPlugin.Extension extension

    TaskBase(final CharSequence description) {
        setGroup(GROUP.toString())
        setDescription(description.toString())
        extension = project.extensions.findByType(SemanticVersionsPlugin.Extension)
        propertiesPath = extension.propertiesPath
    }

    protected Versions read() {
        new Versions(new File(propertiesPath.get().toString()))
    }

    protected Versions write(final Versions versions) {
        versions.write(new File(propertiesPath.get().toString()))
    }

    protected void setGradleVersion(final Versions versions) {
        String version = versions.toString()
        logger.info("SemanticVersionsPlugin: Setting project version as ${version}")
        project.setVersion(version)
    }
}
