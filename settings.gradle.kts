pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/main")
    }
}

rootProject.name = "lobson"

include(":app")
include(":backend") 