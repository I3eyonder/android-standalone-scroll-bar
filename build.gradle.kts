// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
}

extra["group_id"] = "com.hieupt.android"
extra["artifact_id"] = "standalonescrollbar"
extra["version_name"] = "1.1.5"
extra["version_code"] = 7