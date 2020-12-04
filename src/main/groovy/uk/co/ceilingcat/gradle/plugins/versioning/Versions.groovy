package uk.co.ceilingcat.gradle.plugins.versioning

class Versions {

    public static final String KEY_MAJOR = 'major'
    public static final String KEY_MINOR = 'minor'
    public static final String KEY_PATCH = 'patch'

    private static final String DEFAULT_VERSION = '0'
    private static final int MIN_COMPONENT = 0

    private int major
    private int minor
    private int patch

    private static int checkComponent(final int component) throws IllegalArgumentException {
        if (component < MIN_COMPONENT) {
            throw new IllegalArgumentException('Version components must be at least 0.')
        }

        component
    }

    private static int getValidComponentWithDefault(final Properties properties,
                                                    final String propertiesKey,
                                                    final String defaultValue) throws IllegalArgumentException {
        checkComponent(properties.getProperty(propertiesKey, defaultValue) as int)
    }

    Versions(final int major, final int minor, final int patch)
        throws IllegalArgumentException {

        setVersion(major, minor, patch)
    }

    Versions(final File versionsFile) throws IllegalArgumentException {
        if (!versionsFile.exists()) {
            throw new IllegalArgumentException("Versions file [${versionsFile.canonicalPath}] does not exist.")
        }

        versionsFile.withDataInputStream { final InputStream inputStream -> init(inputStream) }
    }

    int getMajor() {
        major
    }

    int getMinor() {
        minor
    }

    int getPatch() {
        patch
    }

    private void init(final InputStream inputStream) throws IllegalArgumentException {
        final Properties properties = new Properties()
        properties.load(inputStream)
        setVersion(getValidComponentWithDefault(properties, KEY_MAJOR, DEFAULT_VERSION),
            getValidComponentWithDefault(properties, KEY_MINOR, DEFAULT_VERSION),
            getValidComponentWithDefault(properties, KEY_PATCH, DEFAULT_VERSION))
    }

    void setVersion(final int major, final int minor, final int patch) {
        this.major = checkComponent(major)
        this.minor = checkComponent(minor)
        this.patch = checkComponent(patch)
    }

    Versions write(final File versionsFile) {
        final Properties properties = new Properties()

        properties.setProperty(KEY_MAJOR, major as String)
        properties.setProperty(KEY_MINOR, minor as String)
        properties.setProperty(KEY_PATCH, patch as String)

        versionsFile.withDataOutputStream { final OutputStream outputStream ->
            properties.store(outputStream, null)
        }

        this
    }

    static final int nextComponentVersion(final int value) { value + 1 }

    Versions majorIncrement() {
        new Versions(nextComponentVersion(this.major), this.minor, this.patch)
    }

    Versions minorIncrement() {
        new Versions(this.major, nextComponentVersion(this.minor), this.patch)
    }

    Versions patchIncrement() {
        new Versions(this.major, this.minor, nextComponentVersion(this.patch))
    }

    @Override
    String toString() {
        "${major}.${minor}.${patch}"
    }
}
