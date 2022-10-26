plugins {
    id("accompanist-mp-plugin")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.foundation)
                api(compose.material)
                api("io.github.qdsfdhvh:material3:1.0.7")
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val androidMain by getting {
            dependencies {
                compileOnly("androidx.compose.material3:material3:1.0.0-rc01")
            }
        }
        val jvmMain by getting {
        }
        val darwinMain by creating {
            dependsOn(commonMain)
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
