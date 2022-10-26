package moe.tlaster.accompanist.plugin

import Versions
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class AccompanistSamplePlugin : Plugin<Project> {

    private lateinit var sampleExtension: AccompanistSampleExtension

    override fun apply(project: Project) {
        with(project) {
            sampleExtension = extensions.create<AccompanistSampleExtension>("sample")
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
                configureIosTarget(sampleExtension.entryPoint)
            }
            macosX64() {
                configureMacosTarget(sampleExtension.entryPoint)
            }
            macosArm64() {
                configureMacosTarget(sampleExtension.entryPoint)
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
                applicationId = sampleExtension.applicationId
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
                    mainClass = sampleExtension.desktopMainClass
                    nativeDistributions {
                        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                        packageName = sampleExtension.packageName
                        packageVersion = sampleExtension.packageVersion
                        modules("jdk.unsupported")
                        modules("jdk.unsupported.desktop")
                        macOS {
                            bundleID = sampleExtension.applicationId
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
                        packageName = sampleExtension.packageName
                        packageVersion = sampleExtension.packageVersion
                    }
                }
            }
            experimental {
                uikit.application {
                    bundleIdPrefix = sampleExtension.applicationId
                    projectName = sampleExtension.packageName
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

open class AccompanistSampleExtension {
    var packageName = ""
    var packageVersion = "1.0.0"
    var applicationId: String = ""
    var desktopMainClass = ""
    var entryPoint: String = ""
}