import org.gradle.api.JavaVersion

object Versions {
    object Android {
        const val min = 21
        const val compile = 33
        const val target = compile
        const val buildTools = "33.0.0"
    }

    object Kotlin {
        const val lang = "1.7.10"
        const val coroutines = "1.6.4"
        const val serialization = "1.4.0"
        const val datetime = "0.4.0"
        const val immutable = "0.3.5"
    }

    object Java {
        const val jvmTarget = "11"
        val java = JavaVersion.VERSION_11
    }

    const val ksp = "${Kotlin.lang}-1.0.6"
    const val kotlinpoet = "1.12.0"
    const val spotless = "6.9.1"
    const val ktlint = "0.46.1"
    const val compose_jb = "1.2.0-alpha01-dev770"
    const val koin = "3.2.0"
    const val ktor = "2.1.0"
    const val okio = "3.2.0"
    const val multiplatformSettings = "1.0.0-alpha01"
    const val sqldelight = "2.0.0-alpha03"
    const val accompanist = "0.26.1-alpha"
    const val precompose = "1.3.3"
    const val precomposeKsp = "1.0.0"
    const val buildkonfig = "0.13.3"
    const val napier = "2.6.1"
    const val zxing = "4.3.0"
    const val safearea = "0.1.3"
    const val paging = "1.0.4"
    const val kfilepicker = "1.0.5"
    const val ktorfit = "1.0.4"
    object AndroidX {
        const val datastore = "1.0.0"
        const val activity = "1.5.1"
        const val core = "1.8.0"
        const val browser = "1.4.0"
        const val material3 = "1.0.0-alpha15"
    }
}
