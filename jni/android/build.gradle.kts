plugins {
    id("com.android.library")
    kotlin("android")
}

kotlin {
    explicitApi()
}

dependencies {
    api(project(":jni"))
    implementation(kotlin("stdlib-jdk8"))
}

android {
    defaultConfig {
        compileSdkVersion(30)
        minSdkVersion(21)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {}
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    externalNativeBuild {
        cmake {
            setPath("src/main/CMakeLists.txt")
        }
    }
    ndkVersion = "21.3.6528147"

    afterEvaluate {
        tasks.withType<com.android.build.gradle.tasks.factory.AndroidUnitTest>().all {
            enabled = false
        }
    }
}

afterEvaluate {
    configure(listOf("Debug", "Release").map { tasks["externalNativeBuild$it"] }) {
        dependsOn(":native:buildSecp256k1Android")
    }
}
