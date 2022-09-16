package com.forevertea.travelstories

object Libs {

    const val junit = "junit:junit:${Versions.jUnit}"


    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreAndroidx}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val tooling = "androidx.ui:ui-tooling:${Versions.uiToolAndroidx}"
        const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}"
        const val fragment = "androidx.fragment:fragment:${Versions.fragment}"

        object Navigation{
            const val fragmentNavigation = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
            const val uiNavigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
            const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.nav_version}"
        }

        object Coroutine{
            const val core =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
        }

        object Room{
            const val room =  "androidx.room:room-runtime:${Versions.room}"
            const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
            const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        }

        object Compose {
            const val runtime_livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
            const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
            const val compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
            const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
            const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val animation = "androidx.compose.animation:animation:${Versions.compose}"
            const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
        }

        object Coil{
            const val coil_compose = "io.coil-kt:coil-compose:${Versions.coil}"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"
            const val junit = "androidx.test.ext:junit-ktx:${Versions.junitAndroidx}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
            const val junitCompose = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        }
    }
}
