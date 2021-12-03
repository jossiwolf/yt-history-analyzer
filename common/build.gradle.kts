import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.0"
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization") version "1.5.31"
}

group = "de.jossiwolf"
version = "1.0"

sqldelight {
    database("VideoDatabase") {
        packageName = "de.jossiwolf.ytanalyzer"
    }
}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
                implementation("io.ktor:ktor-client-core:1.6.6")
                implementation("io.ktor:ktor-client-cio:1.6.6")
                implementation("io.ktor:ktor-serialization:1.6.6")
                implementation("io.ktor:ktor-client-logging:1.6.6")
                implementation("io.ktor:ktor-server-core:1.6.6")
                implementation("io.ktor:ktor-server-netty:1.6.6")
                implementation("ch.qos.logback:logback-classic:1.2.6")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.2.0")
                api("androidx.core:core-ktx:1.3.1")
                api("com.squareup.sqldelight:android-driver:1.5.3")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
                api("ch.qos.logback:logback-classic:1.2.6")
                api("com.squareup.sqldelight:sqlite-driver:1.5.3")
            }
        }
        val desktopTest by getting
    }
}

repositories {
    mavenCentral()
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(30)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
