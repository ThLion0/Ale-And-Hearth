plugins {
    id("java")
}

group = property("maven_group") as String
version = property("plugin_version") as String

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("libs/HytaleServer.jar"))
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("manifest.json") {
            expand("version" to project.version)
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from("src/main/resources")
    }
}