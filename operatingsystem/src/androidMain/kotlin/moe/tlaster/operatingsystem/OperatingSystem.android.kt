package moe.tlaster.operatingsystem

actual val currentOperatingSystem: OperatingSystem
    get() = OperatingSystem.Android

actual val OperatingSystem.isDesktop: Boolean
    get() = false
