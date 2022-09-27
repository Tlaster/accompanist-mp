plugins {
    id("accompanist-mp-plugin")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.foundation)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.material3)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:${Versions.compose}")
                implementation("androidx.compose.foundation:foundation:${Versions.compose}")
                implementation("androidx.compose.material3:material3:${Versions.compose_material3}")
            }
        }
        val material3Main by creating {
            dependsOn(commonMain)
        }
        val jvmMain by getting {
            dependsOn(material3Main)
        }
        val darwinMain by creating {
            dependsOn(material3Main)
        }
        val uikitMain by getting {
            dependsOn(darwinMain)
        }
        val macosX64Main by getting {
            dependsOn(darwinMain)
        }
        val macosArm64Main by getting {
            dependsOn(darwinMain)
        }
    }
}
