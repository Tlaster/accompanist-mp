
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

fun com.android.build.gradle.internal.dsl.BaseAppModuleExtension.setupApplication() {
    setup()
    defaultConfig {
        applicationId = Package.id
        targetSdk = Versions.Android.target
        versionCode = Package.versionCode
        versionName = Package.versionName
    }
}

private fun <BuildFeaturesT : BuildFeatures, BuildTypeT : BuildType, DefaultConfigT : DefaultConfig, ProductFlavorT : ProductFlavor>
    com.android.build.api.dsl.CommonExtension<BuildFeaturesT, BuildTypeT, DefaultConfigT, ProductFlavorT>.setup() {
    compileSdk = Versions.Android.compile
    buildToolsVersion = Versions.Android.buildTools
    defaultConfig {
        minSdk = Versions.Android.min
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = Versions.Java.java
        targetCompatibility = Versions.Java.java
    }
}

fun com.android.build.api.dsl.LibraryExtension.setupLibrary() {
    setup()
    defaultConfig {
        targetSdk = Versions.Android.target
        consumerProguardFiles("consumer-rules.pro")
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["debug"].java.srcDir("build/generated/ksp/android/androidDebug/kotlin")
}

fun KotlinJvmTarget.setupJvm() {
    compilations.all {
        kotlinOptions.jvmTarget = Versions.Java.jvmTarget
    }
}
