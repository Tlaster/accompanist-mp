package moe.tlaster.accompanist.plugin

import Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class AccompanistSamplePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
            kmpConfig()
            androidConfig()
            composeConfig()
        }
    }

    private fun Project.applyPlugins() {
        with(plugins) {
            apply("kotlin-multiplatform")
            apply("com.android.application")
            apply("org.jetbrains.compose")
            // apply("org.jetbrains.kotlin.android")
        }
    }

    private fun Project.kmpConfig() {
        kotlin {
            android()
            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = Versions.Java.jvmTarget
                }
            }
            ios("uikit") {
                configureIosTarget(findProperty("entryPoint") as String)
            }
            macosX64() {
                configureMacosTarget(findProperty("entryPoint") as String)
            }
            macosArm64() {
                configureMacosTarget(findProperty("entryPoint") as String)
            }

            sourceSets.apply {
                getByName("androidMain") {
                    dependencies {
                        implementation("androidx.activity:activity-compose:1.6.0")
                    }
                }
                val commonMain = getByName("commonMain")
                getByName("jvmMain") {
                    dependencies {
                        implementation(compose.desktop.currentOs)
                    }
                }
                val macosMain = maybeCreate("macosMain")
                macosMain.dependsOn(commonMain)
                getByName("macosX64Main") {
                    dependsOn(macosMain)
                }
                getByName("macosArm64Main") {
                    dependsOn(macosMain)
                }
            }
        }
    }

    private fun Project.androidConfig() {
        android {
            compileSdk = Versions.Android.compile
            buildToolsVersion = Versions.Android.buildTools
            defaultConfig {
                applicationId = findProperty("applicationId") as String
                minSdk = Versions.Android.min
                targetSdk = Versions.Android.target
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = Versions.Java.java
                targetCompatibility = Versions.Java.java
            }
            sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }

    private fun Project.composeConfig() {
        compose {
            desktop {
                application {
                    mainClass = findProperty("desktopMainClass") as String
                    nativeDistributions {
                        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                        packageName = findProperty("packageName") as String
                        packageVersion = findProperty("packageVersion") as String
                        modules("jdk.unsupported")
                        modules("jdk.unsupported.desktop")
                        macOS {
                            bundleID = findProperty("applicationId") as String
                        }
                    }
                }
                nativeApplication {
                    targets(
                        kotlin.targets.getByName("macosX64"),
                        kotlin.targets.getByName("macosArm64"),
                    )
                    distributions {
                        targetFormats(TargetFormat.Dmg)
                        packageName = findProperty("packageName") as String
                        packageVersion = findProperty("packageVersion") as String
                    }
                }
            }
            experimental {
                uikit.application {
                    bundleIdPrefix = findProperty("applicationId") as String
                    projectName = findProperty("packageName") as String
                    deployConfigurations {
                        simulator("Simulator") {
                            device =
                                org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_MINI
                        }
                    }
                }
            }
        }
    }

    private fun Project.applyConfig() {

    }
}

private fun KotlinNativeTarget.configureIosTarget(entryPoint: String) {
    binaries {
        executable {
            entryPoint(entryPoint)
            freeCompilerArgs += listOf(
                "-linker-option",
                "-framework",
                "-linker-option",
                "Metal",
                "-linker-option",
                "-framework",
                "-linker-option",
                "CoreText",
                "-linker-option",
                "-framework",
                "-linker-option",
                "CoreGraphics"
            )
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            binaryOptions["memoryModel"] = "experimental"
        }
    }
}

private fun KotlinNativeTarget.configureMacosTarget(entryPoint: String) {
    binaries {
        executable {
            entryPoint(entryPoint)
            freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal"
            )
            freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
            binaryOptions["memoryModel"] = "experimental"
        }
    }
}
