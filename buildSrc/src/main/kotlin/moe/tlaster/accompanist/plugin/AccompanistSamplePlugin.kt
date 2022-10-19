package moe.tlaster.accompanist.plugin

import Versions
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.experimental.dsl.ExperimentalExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.targets

class AccompanistSamplePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.create("sample", AccompanistSampleExtension::class.java)
            applyPlugins()
            kmpConfig(extension)
            androidConfig(extension)
            composeConfig(extension)
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

    private fun Project.kmpConfig(extension: AccompanistSampleExtension) {
        extensions.configure<KotlinMultiplatformExtension> {
            android()
            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = Versions.Java.jvmTarget
                }
                // testRuns.getByName("test").executionTask.configure {
                //     useJUnitPlatform()
                // }
            }
            ios("uikit") {
                configureIosTarget(extension.entryPoint)
            }
            macosX64() {
                configureMacosTarget(extension.entryPoint)
            }
            macosArm64() {
                configureMacosTarget(extension.entryPoint)
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

    private fun Project.androidConfig(extension: AccompanistSampleExtension) {
        extensions.configure<BaseAppModuleExtension> {
            compileSdk = Versions.Android.compile
            buildToolsVersion = Versions.Android.buildTools
            defaultConfig {
                applicationId = extension.applicationId
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

    private fun Project.composeConfig(extension: AccompanistSampleExtension) {
        extensions.configure<ComposeExtension> {
            this.extensions.configure<DesktopExtension> {
                application {
                    mainClass = extension.desktopMainClass
                    nativeDistributions {
                        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                        packageName = extension.packageName
                        packageVersion = extension.packageVersion
                        modules("jdk.unsupported")
                        modules("jdk.unsupported.desktop")
                        macOS {
                            bundleID = extension.applicationId
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
                        packageName = extension.packageName
                        packageVersion = extension.packageVersion
                    }
                }
            }
            this.extensions.configure<ExperimentalExtension> {
                uikit.application {
                    bundleIdPrefix = extension.applicationId
                    projectName = extension.packageName
                    deployConfigurations {
                        simulator("Simulator") {
                            device = org.jetbrains.compose.experimental.dsl.IOSDevices.IPHONE_13_MINI
                        }
                    }
                }
            }
        }
    }

    private val Project.kotlin get() = extensions.findByType<KotlinMultiplatformExtension>()!!
    private val Project.compose get() = (kotlin as org.gradle.api.plugins.ExtensionAware)
        .extensions.getByName("compose") as org.jetbrains.compose.ComposePlugin.Dependencies
}

private fun KotlinNativeTarget.configureIosTarget(entryPoint: String) {
    binaries {
        executable {
            entryPoint(entryPoint)
            freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal",
                "-linker-option", "-framework", "-linker-option", "CoreText",
                "-linker-option", "-framework", "-linker-option", "CoreGraphics"
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