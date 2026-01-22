plugins {
    id("java")
}

group = "com.thlion_"
version = "0.0.3"

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