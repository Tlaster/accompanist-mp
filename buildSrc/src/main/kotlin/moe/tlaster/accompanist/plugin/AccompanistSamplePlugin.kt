package moe.tlaster.accompanist.plugin

import Versions
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class AccompanistSamplePlugin : Plugin<Project> {

    private val Project.android get() = extensions.findByType<BaseAppModuleExtension>()!!
    private val Project.kmp get() = extensions.findByType<KotlinMultiplatformExtension>()!!

    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            androidConfig()
            kmpConfig()
        }
    }

    private fun Project.kmpConfig() {
        with(kmp) {
            val entryPoint = findProperty("ENTRY_POINT") as String
            android()
            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = Versions.Java.jvmTarget
                }
                testRuns.getByName("test").executionTask.configure {
                    useJUnitPlatform()
                }
            }
            ios("uikit") {
                configureIosTarget(entryPoint)
            }
            macosX64() {
                configureMacosTarget(entryPoint)
            }
            macosArm64() {
                configureMacosTarget(entryPoint)
            }

            sourceSets.apply {
                getByName("androidMain") {
                    dependencies {
                        implementation("androidx.activity:activity-compose:1.6.0")
                    }
                }
                val commonMain = getByName("commonMain")
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
        with(android) {
            compileSdk = Versions.Android.compile
            buildToolsVersion = Versions.Android.buildTools
            defaultConfig {
                applicationId = findProperty("ANDROID_NAMESPACE") as String
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

    private fun Project.applyPlugins() {
        with(plugins) {
            apply("kotlin-multiplatform")
            apply("com.android.application")
            apply("org.jetbrains.compose")
            // apply("org.jetbrains.kotlin.android")
        }
    }
}

private fun KotlinNativeTarget.configureIosTarget(entryPoint: String) {
    binaries {
        executable {
            entryPoint(entryPoint)
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-linker-option", "-framework", "-linker-option", "Metal",
                "-linker-option", "-framework", "-linker-option", "CoreText",
                "-linker-option", "-framework", "-linker-option", "CoreGraphics"
            )
            // TODO: the current compose binary surprises LLVM, so disable checks for now.
            freeCompilerArgs = freeCompilerArgs + "-Xdisable-phases=VerifyBitcode"
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
        }
    }
}