plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
    implementation("org.jetbrains.compose:compose-gradle-plugin:1.2.0")
    implementation(kotlin("gradle-plugin", version = "1.7.20"))
}

gradlePlugin {
    plugins {
        register("accompanist-mp-plugin") {
            id = "accompanist-mp-plugin"
            implementationClass = "moe.tlaster.accompanist.plugin.AccompanistMultiplatformPlugin"
        }
        register("accompanist-sample-plugin") {
            id = "accompanist-sample-plugin"
            implementationClass = "moe.tlaster.accompanist.plugin.AccompanistSamplePlugin"
        }
    }
}