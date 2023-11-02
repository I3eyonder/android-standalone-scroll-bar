plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
}

android {
    namespace = "com.hieupt.android.standalonescrollbar"
    compileSdk = 33

    defaultConfig {
        minSdk = 17

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    publishing {
        publishing {
            singleVariant("release") {
                withSourcesJar()
                withJavadocJar()
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.interpolator:interpolator:1.0.0")
    compileOnly("androidx.recyclerview:recyclerview:1.3.2")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = rootProject.extra["group_id"].toString()
            artifactId = rootProject.extra["artifact_id"].toString()
            version = rootProject.extra["version_name"].toString()

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
