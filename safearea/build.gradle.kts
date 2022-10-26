plugins {
    id("accompanist-mp-plugin")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.foundation)
                api(compose.ui)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:${Versions.compose}")
                implementation("androidx.compose.foundation:foundation:${Versions.compose}")
            }
        }
        val desktopMain by creating {
            dependsOn(commonMain)
        }
        val jvmMain by getting {
            dependsOn(desktopMain)
        }
        val macosMain by getting {
            dependsOn(desktopMain)
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
    }
}
