package uk.co.ceilingcat.gradle.plugins.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.GroovyBasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Property
import uk.co.ceilingcat.gradle.plugins.versioning.tasks.MajorIncrement
import uk.co.ceilingcat.gradle.plugins.versioning.tasks.MinorIncrement
import uk.co.ceilingcat.gradle.plugins.versioning.tasks.PatchIncrement
import uk.co.ceilingcat.gradle.plugins.versioning.tasks.SetProjectVersion

class SemanticVersionsPlugin implements Plugin<Project> {

    static final String EXTENSION_NAME = 'versions'
    static final String VERSIONS_PROPERTIES_RELATIVE_PATH = '.semantic-versions/versions.properties'

    static class Extension {

        final Property<CharSequence> propertiesPath

        Extension(final Project project) {
            propertiesPath = project.objects.property(CharSequence)

            final File defaultInputPath = new File(project.projectDir, VERSIONS_PROPERTIES_RELATIVE_PATH)
            propertiesPath.set(defaultInputPath.canonicalPath)
        }
    }

    void apply(final Project project) {
        project.getPluginManager().apply(GroovyBasePlugin)
        project.getPluginManager().apply(JavaPlugin)

        configure(project)
    }

    static void configure(final Project project) {
        project.extensions.create(EXTENSION_NAME, Extension, project)
        project.tasks.with {
            create(SetProjectVersion.NAME, SetProjectVersion)
            create(MajorIncrement.NAME, MajorIncrement)
            create(MinorIncrement.NAME, MinorIncrement)
            create(PatchIncrement.NAME, PatchIncrement)
        }
    }
}
