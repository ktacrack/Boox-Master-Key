plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.boox.masterkey"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.boox.masterkey"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// ← AFEGEIX AIXÒ AL FINAL (FORA del bloc android)
tasks.register("renameApk") {
    doLast {
        val apkDir = file("${layout.buildDirectory.get()}/outputs/apk/release")
        if (apkDir.exists()) {
            apkDir.listFiles()?.forEach { file ->
                if (file.name.endsWith(".apk")) {
                    val newFile = File(apkDir, "BooxMasterKey-v${android.defaultConfig.versionName}.apk")
                    file.renameTo(newFile)
                    println("APK renamed to: ${newFile.name}")
                }
            }
        }
    }
}

tasks.named("assembleRelease") {
    finalizedBy("renameApk")
}
