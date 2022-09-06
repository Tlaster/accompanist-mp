plugins {
    id("accompanist-mp-plugin")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.foundation)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
    }
}
