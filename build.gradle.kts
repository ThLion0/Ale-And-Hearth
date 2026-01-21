plugins {
    id("java")
}

group = "com.thlion_"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("libs/HytaleServer.jar"))
}

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from("src/main/resources")

        destinationDirectory = file("C:/Users/egorv/AppData/Roaming/Hytale/UserData/Mods")
    }
}