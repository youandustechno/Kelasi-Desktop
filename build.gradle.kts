import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.*

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.clovis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven ( "https://repo.spring.io/release")
    maven ("https://repository.jboss.org/maven2")
    google()
}

dependencies {

    implementation(compose.desktop.currentOs)
    implementation("uk.co.caprica:vlcj:4.7.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
    implementation("org.xerial:sqlite-jdbc:3.41.2.2")

    val osName = System.getProperty("os.name").lowercase(Locale.getDefault())

    if(osName.contains("mac")) {
        implementation("org.openjfx:javafx-base:17.0.2:mac")
        implementation("org.openjfx:javafx-graphics:17.0.2:mac")
        implementation("org.openjfx:javafx-controls:17.0.2:mac")
    } else if(osName.contains("win")) {
        implementation("org.openjfx:javafx-base:17.0.2:win")
        implementation("org.openjfx:javafx-graphics:17.0.2:win")
        implementation("org.openjfx:javafx-controls:17.0.2:win")
    } else if(osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
        implementation("org.openjfx:javafx-base:17.0.2:linux")
        implementation("org.openjfx:javafx-graphics:17.0.2:linux")
        implementation("org.openjfx:javafx-controls:17.0.2:linux")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Mateya"
            packageVersion = "1.0.0"
            modules("java.sql")
            macOS {
                bundleID = "com.clovis.Mateya"
                //arch = listOf("x64", "arm64") // Specify architectures to build universal binary
            }
        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if(it.isDirectory) it else zipTree(it)})
}

//tasks.test {
//    useJUnitPlatform()
//}

//kotlin {
//    jvmToolchain(17)
//}