import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.maven.publish)
}

configure<LibraryExtension> {
    namespace = "com.hieupt.android.standalonescrollbar"
    compileSdk = 36

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
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

// Configure Kotlin compiler options using the new compilerOptions DSL (Kotlin Gradle Plugin 2.2+)
// Replaces the deprecated kotlinOptions { jvmTarget = "17" }
tasks.withType<KotlinCompile>().configureEach {
    // Use the new compilerOptions DSL
    compilerOptions {
        // JvmTarget as a String can be provided by JvmTarget.fromTarget("17")
        jvmTarget.set(JvmTarget.fromTarget("17"))
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
                val candidateNames = listOf("release", "androidRelease", "kotlin")
                val component =
                    candidateNames.firstNotNullOfOrNull { components.findByName(it) }
                if (component != null) {
                    from(component)
                } else {
                    throw GradleException("No publishable component found for project '${project.name}'. Searched: $candidateNames. Run './gradlew :${project.path}:tasks --all' to inspect available components.")
                }
            }
        }
    }
}