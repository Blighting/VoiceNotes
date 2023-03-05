plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.voicenotes"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "DATABASE_NAME", "\"Notes database\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        if (project.findProperty("enableComposeCompilerReports") == "true") {
            val outputDir = project.buildDir.path + "/compose-reports"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$outputDir",
                "-P",
                "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$outputDir"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    namespace = "com.example.voicenotes"
}
val composeVersion = "1.3.3"
val composeActivityVersion = "1.6.1"
val composeNavigationVersion = "2.5.3"
val composeSystemUiControllerVersion = "0.18.0"
val koinCoreVersion = "3.3.3"
val koinAndroidVersion = "3.3.3"
val koinAndroidxCompose = "3.4.2"
val androidLifecycleVersion = "2.5.1"
val androidCoreVersion = "1.9.0"
val junitVersion = "4.13.2"
val androidJunitVersion = "1.1.4"
val espressoVersion = "3.5.0"
val roomVersion = "2.4.3"
val loggingInterceptorVersion = "4.10.0"
val media3Version = "1.0.0-beta03"
val accompanistVersion = "0.28.0"

dependencies {
    //Media3
    // For exposing and controlling media sessions
    implementation("androidx.media3:media3-session:$media3Version")
    // For loading data using the OkHttp network stack
    implementation("androidx.media3:media3-datasource-okhttp:$media3Version")
    // For media playback using ExoPlayer
    implementation("androidx.media3:media3-exoplayer:$media3Version")
    // Common functionality for media database components
    implementation("androidx.media3:media3-database:$media3Version")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    //Room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    //Koin
    implementation("io.insert-koin:koin-core:$koinCoreVersion")
    implementation("io.insert-koin:koin-android:$koinAndroidVersion")
    implementation("io.insert-koin:koin-androidx-compose:$koinAndroidxCompose")

    //Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-rc01")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("com.google.accompanist:accompanist-permissions:$accompanistVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$androidLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$androidLifecycleVersion")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.navigation:navigation-compose:$composeNavigationVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$composeSystemUiControllerVersion")

    implementation("androidx.core:core-ktx:$androidCoreVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
}