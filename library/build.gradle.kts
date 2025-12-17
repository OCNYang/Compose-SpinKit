plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("maven-publish")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
        publishLibraryVariants("release", "debug")
    }

    jvm("desktop") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    // iOS targets - uncomment when ready to support iOS
    // listOf(
    //     iosX64(),
    //     iosArm64(),
    //     iosSimulatorArm64()
    // ).forEach { iosTarget ->
    //     iosTarget.binaries.framework {
    //         baseName = "ComposeSpinKit"
    //         isStatic = true
    //     }
    // }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(compose.uiTooling)
                implementation(compose.preview)
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
            }
        }

        // iOS source sets - uncomment when iOS targets are enabled
        // val iosX64Main by getting
        // val iosArm64Main by getting
        // val iosSimulatorArm64Main by getting
        // val iosMain by creating {
        //     dependsOn(commonMain)
        //     iosX64Main.dependsOn(this)
        //     iosArm64Main.dependsOn(this)
        //     iosSimulatorArm64Main.dependsOn(this)
        // }
    }
}

android {
    namespace = "com.ocnyang.compose_loading"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.ocnyang"
            artifactId = "compose-spinkit"
            version = "2.0.0"
        }
    }
}
