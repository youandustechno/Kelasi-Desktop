import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.*

plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.openjfx.javafxplugin") version "0.0.10"
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

    implementation("org.jdatepicker:jdatepicker:1.3.4")
    //implementation("org.jetbrains.compose.web:web-core-desktop:1.6.2")
    implementation("org.openjfx:javafx-controls:17")
    implementation("org.openjfx:javafx-web:17")
    implementation("org.apache.pdfbox:pdfbox:2.0.29")
    //implementation("io.github.KevinnZou:compose-webview:0.33.3")
    implementation("org.jetbrains.compose.web:web-core:1.0.0")
    implementation("org.apache.pdfbox:pdfbox:2.0.27")

    implementation("com.optimaize.languagedetector:language-detector:0.6")
    implementation("com.google.guava:guava:31.1-jre")

    //implementation("com.google.cloud:google-cloud-texttospeech:2.37.0")
    implementation("javazoom:jlayer:1.0.1")

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