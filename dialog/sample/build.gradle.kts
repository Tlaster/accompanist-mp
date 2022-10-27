plugins {
    id("accompanist-sample-plugin")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(projects.dialog)
            }
        }
    }
}
