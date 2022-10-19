import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("accompanist-sample-plugin")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(projects.dialog)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        // val jvmMain by getting {
        //     dependencies {
        //         implementation(compose.desktop.currentOs)
        //     }
        // }
    }
}

sample {
    packageName = "MPDialogSample"
    applicationId = "moe.tlaster.dialog.sample"
    desktopMainClass = "moe.tlaster.dialog.sample.MainKt"
    entryPoint = "moe.tlaster.dialog.sample.main"
}
