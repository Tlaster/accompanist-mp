object Package {
    const val group = "moe.tlaster"
    const val name = "accompanist-mp"
    val id = "${group}.accompanist.mp"
    val versionName =
        "${Version.main}.${Version.mirror}.${Version.patch}${if (Version.revision.isNotEmpty()) "-${Version.revision}" else ""}"
    const val versionCode = Version.build

    object Version {
        const val main = "0"
        const val mirror = "2"
        const val patch = "0"
        const val revision = "dev09"
        const val build = 9
    }
}
