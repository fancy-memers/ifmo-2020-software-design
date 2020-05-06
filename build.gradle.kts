plugins {
    java
    kotlin("jvm") version "1.3.70"
    application
    kotlin("plugin.serialization") version "1.3.70"
}

group = "org.fancy.memers"
version = "0.1"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
    jcenter()
    maven("https://jitpack.io/")
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api("org.hexworks.zircon:zircon.core-jvm:2020.0.2-PREVIEW")
    api("org.hexworks.zircon:zircon.jvm.swing:2020.0.2-PREVIEW")
    api("com.squareup.moshi:moshi:1.9.2")
    api("com.squareup.moshi:moshi-kotlin:1.9.2")
    api("com.squareup.moshi:moshi-adapters:1.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
}

application {
    mainClassName = "org.fancy.memers.MainKt"
}
