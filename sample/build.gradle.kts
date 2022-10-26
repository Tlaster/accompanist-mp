import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.compose_jb
//    id("com.android.application")
}

kotlin {
    ios("uikit") {
        binaries {
            executable {
                entryPoint = "moe.tlaster.accompanist.sample.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal",
                    "-linker-option", "-framework", "-linker-option", "CoreText",
                    "-linker-option", "-framework", "-linker-option", "CoreGraphics"
                )
            }
        }
    }
//    android()
    macosX64 {
        binaries {
            executable {
                entryPoint = "moe.tlaster.accompanist.sample.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal"
                )
            }
        }
    }
    macosArm64 {
        binaries {
            executable {
                entryPoint = "moe.tlaster.accompanist.sample.main"
                freeCompilerArgs += listOf(
                    "-linker-option", "-framework", "-linker-option", "Metal"
                )
            }
        }
    }

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = Versions.Java.jvmTarget
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(project(":safearea"))
                implementation(project(":mediaquery"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
//        val androidMain by getting {
//            dependencies {
//                implementation("androidx.activity:activity-ktx:1.6.0")
//                implementation("androidx.activity:activity-compose:1.6.0")
//            }
//        }
        val darwinMain by creating {
            dependsOn(commonMain)
            dependencies {
            }
        }
        val uikitMain by getting {
            dependsOn(darwinMain)
            dependencies {
            }
        }
        val macosMain by creating {
            dependsOn(darwinMain)
            dependencies {
            }
        }
        val macosX64Main by getting {
            dependsOn(macosMain)
        }
        val macosArm64Main by getting {
            dependsOn(macosMain)
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            binaryOptions["memoryModel"] = "experimental"
        }
    }
}

compose {
    desktop {
        application {
            mainClass = "moe.tlaster.accompanist.sample.MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "moe.tlaster.accompanist.sample"
                packageVersion = "1.0.0"
                macOS {
                    bundleID = "moe.tlaster.accompanist.sample"
                    // iconFile.set(project.file("src/jvmMain/resources/icon/ic_launcher.icns"))
                }
                linux {
                    // iconFile.set(project.file("src/jvmMain/resources/icon/ic_launcher.png"))
                }
                windows {
                    shortcut = true
                    menu = true
                    // iconFile.set(project.file("src/jvmMain/resources/icon/ic_launcher.ico"))
                }
            }
        }
        nativeApplication {
            targets(kotlin.targets.getByName("macosX64"), kotlin.targets.getByName("macosArm64"))
            distributions {
                targetFormats(TargetFormat.Dmg)
                packageName = "moe.tlaster.accompanist.sample"
                packageVersion = "1.0.0"
                macOS {
                    bundleID = "moe.tlaster.accompanist.sample"
                    // iconFile.set(project.file("src/jvmMain/resources/icon/ic_launcher.icns"))
                }
            }
        }
    }
    experimental {
        uikit {
            application {
                bundleIdPrefix = "moe.tlaster.accompanist.sample"
                projectName = "AccompanistSample"
                deployConfigurations {
                    simulator("Simulator2") {
                        device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_MINI
                    }
                }
            }
        }
    }
}

//android {
//    compileSdk = Versions.Android.compile
//    buildToolsVersion = Versions.Android.buildTools
//    namespace = "moe.tlaster.accompanist.sample"
//    defaultConfig {
//        applicationId = "moe.tlaster.accompanist.sample"
//        minSdk = Versions.Android.min
//        targetSdk = Versions.Android.target
//        versionCode = 1
//        versionName = "0.1.0"
//    }
//    sourceSets {
//        getByName("main") {
//            manifest.srcFile("src/androidMain/AndroidManifest.xml")
//        }
//    }
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//        }
//    }
//    compileOptions {
//        sourceCompatibility = Versions.Java.java
//        targetCompatibility = Versions.Java.java
//    }
//}
