import org.jetbrains.compose.desktop.application.dsl.TargetFormat

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
        val commonTest by getting {
            dependencies {
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose {
    experimental {
        uikit.application {
            bundleIdPrefix = "moe.tlaster.dialog.sample"
            projectName = "MPDialogSample"
            deployConfigurations {
                simulator("Simulator") {
                    device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_MINI
                }
            }
        }
    }
    desktop {
        application {
            mainClass = "moe.tlaster.dialog.sample.MainKt"
            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "MPDialogSample"
                packageVersion = "1.0.0"
                modules("jdk.unsupported")
                modules("jdk.unsupported.desktop")
                macOS {
                    bundleID = "moe.tlaster.dialog.sample"
                }
            }
        }
        nativeApplication {
            targets(kotlin.targets.getByName("macosX64"), kotlin.targets.getByName("macosArm64"))
            distributions {
                targetFormats(TargetFormat.Dmg)
                packageName = "MPDialogSample"
                packageVersion = "1.0.0"
            }
        }
    }
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.all {
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            binaryOptions["memoryModel"] = "experimental"
        }
    }
}
