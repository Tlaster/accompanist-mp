package moe.tlaster.operatingsystem

enum class OperatingSystem {
    Android, iOS, Windows, Linux, MacOS, Unknown,
}

expect val currentOperatingSystem: OperatingSystem

expect val OperatingSystem.isDesktop: Boolean
