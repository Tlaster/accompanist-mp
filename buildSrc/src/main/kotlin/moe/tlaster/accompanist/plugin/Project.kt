package moe.tlaster.accompanist.plugin

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.experimental.dsl.ExperimentalExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.android(action: BaseAppModuleExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.ext(action: ExtraPropertiesExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.publish(action: PublishingExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.sign(action: SigningExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.compose(action: ComposeExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun ComposeExtension.desktop(action: DesktopExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun ComposeExtension.experimental(action: ExperimentalExtension.() -> Unit) {
    extensions.configure(action)
}

internal val Project.kotlin: KotlinMultiplatformExtension
    get() = extensions.findByType()!!

internal val Project.compose: ComposePlugin.Dependencies
    get() = (kotlin as ExtensionAware).extensions.findByType()!!

internal val Project.ext: ExtraPropertiesExtension
    get() = extensions.findByType()!!