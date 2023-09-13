pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://maven.fabric.io/public") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://maven.fabric.io/public") }
    }
}

rootProject.name = "DanGam"
include(":app")
