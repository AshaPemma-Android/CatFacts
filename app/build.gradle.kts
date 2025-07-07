import com.android.build.gradle.internal.packaging.defaultExcludes

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
}
apply(plugin = "jacoco")

android {
    namespace = "com.example.catfacts"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.catfacts"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.example.catfacts.HiltTestRunner"
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        testInstrumentationRunner = "com.example.catfacts.HiltTestRunner"
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            pickFirsts += listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE.md",
                "META-INF/DEPENDENCIES"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.hilt.android.testing)
    testImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)
}
tasks.register<JacocoReport>("jacocoTestReport") {
    // Ensure both test types are executed before generating report
    dependsOn("testDebugUnitTest", "connectedDebugAndroidTest")

    // Configure report outputs
    reports {
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/report.xml"))
        csv.required.set(false)
    }

    // File filters to exclude generated and irrelevant classes
    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*Hilt*.*",
        "**/*_Factory.class",
        "**/*_Impl.class",
        "**/*JsonAdapter.*"
    )

    // Class files from Java and Kotlin debug build output
    val debugTree = fileTree("${layout.buildDirectory}/intermediates/javac/debug/classes") {
        exclude(fileFilter)
    }
    val kotlinDebugTree = fileTree("${layout.buildDirectory}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    // Main source directories
    val mainSrc = files(
        "$projectDir/src/main/java",
        "$projectDir/src/main/kotlin"
    )

    sourceDirectories.setFrom(mainSrc)
    classDirectories.setFrom(files(debugTree, kotlinDebugTree))

    // Coverage execution data (unit and instrumentation)
    val coverageFiles = files(
        "${buildDir}/jacoco/testDebugUnitTest.exec",
        "${buildDir}/outputs/code-coverage/connected/connected-debug.ec"
    ).filter { it.exists() }

    executionData.setFrom(coverageFiles)

    // Print useful output after report is generated
    doLast {
        println("✅ JaCoCo report generated:")
        println("   HTML report: ${layout.buildDirectory.dir("reports/jacoco/html").get().asFile}")
        println(
            "   XML report:  ${
                layout.buildDirectory.file("reports/jacoco/report.xml").get().asFile
            }"
        )
        println("   Included coverage files:")
        coverageFiles.files.forEach { println("    - ${it.absolutePath}") }
    }
}