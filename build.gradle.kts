plugins {
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    kotlin("multiplatform").apply(false)
    id("org.jetbrains.compose").apply(false)
    id("com.diffplug.spotless").version(Versions.spotless)
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = Versions.Java.jvmTarget
            allWarningsAsErrors = true
        }
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            targetExclude("$buildDir/**/*.kt", "bin/**/*.kt", "buildSrc/**/*.kt")
            ktlint(Versions.ktlint).editorConfigOverride(
                mapOf(
                    // rules: https://github.com/pinterest/ktlint/blob/master/README.md#standard-rules
                    "disabled_rules" to "filename,enum-entry-name-case,trailing-comma"
                )
            )
        }
        kotlinGradle {
            target("*.gradle.kts")
            ktlint(Versions.ktlint)
        }
    }
}
