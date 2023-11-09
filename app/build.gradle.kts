plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.lidiwo.cloudmusic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lidiwo.cloudmusic"
        minSdk = 26
        targetSdk = 34
        versionCode =1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.emoji:emoji:1.1.0")
    implementation("androidx.emoji:emoji-bundled:1.1.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")


    implementation("com.geyifeng.immersionbar:immersionbar:3.2.2")


    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
//    implementation("")
//
//
//
//    compile 'io.reactivex.rxjava2:rxjava:2.1.7'
//    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
//
//    compile 'com.alibaba:fastjson:1.2.44'
//    compile 'com.squareup.retrofit2:retrofit:2.3.0'
//    compile 'com.squareup.okhttp3:logging-interceptor:3.9.1'
//    compile 'com.squareup.retrofit2:adapter-rxjava:2.3.0'
}
