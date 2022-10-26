package moe.tlaster.operatingsystem

actual val currentOperatingSystem: OperatingSystem
    get() = OperatingSystem.MacOS

actual val OperatingSystem.isDesktop: Boolean
    get() = true
