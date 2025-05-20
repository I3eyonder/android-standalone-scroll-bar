plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

android {
    namespace = "com.hieupt.android.standalonescrollbar"
    compileSdk = 35

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.interpolator)
    compileOnly(libs.androidx.recyclerview)
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