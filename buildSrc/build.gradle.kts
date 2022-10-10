plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.2")
    api(kotlin("gradle-plugin", version = "1.7.20"))
}

gradlePlugin {
    plugins {
        register("accompanist-mp-plugin") {
            id = "accompanist-mp-plugin"
            implementationClass = "moe.tlaster.accompanist.plugin.AccompanistMultiplatformPlugin"
        }
    }
}