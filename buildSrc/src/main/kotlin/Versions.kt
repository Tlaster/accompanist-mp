import org.gradle.api.JavaVersion

object Versions {
    object Android {
        const val min = 21
        const val compile = 33
        const val target = compile
        const val buildTools = "33.0.0"
    }

    object Java {
        const val jvmTarget = "11"
        val java = JavaVersion.VERSION_11
    }

    const val spotless = "6.9.1"
    const val ktlint = "0.46.1"
    const val compose = "1.2.1"
    const val compose_jb = "1.2.0-beta02"
}
