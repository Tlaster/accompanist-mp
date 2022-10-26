package moe.tlaster.operatingsystem

actual val currentOperatingSystem: OperatingSystem
    get() = OperatingSystem.iOS

actual val OperatingSystem.isDesktop: Boolean
    get() = false
