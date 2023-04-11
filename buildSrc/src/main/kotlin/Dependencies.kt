import org.gradle.api.JavaVersion

object SdkVersion {
    const val compileSdkVersion = 33
    const val minSdkVersion = 23
    const val targetSdkVersion = 33

    val javaVersion = JavaVersion.VERSION_1_8
    const val jvmTarget = "1.8"


}

object Versions {
    const val glide = "4.15.0"
}

object Libraries {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
}