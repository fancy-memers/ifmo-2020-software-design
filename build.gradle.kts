plugins {
    java
    kotlin("jvm") version "1.4-M1"
}

group = "org.fancy.memers"
version = "1.0"

repositories {
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    mavenCentral()
    jcenter()
    maven("https://jitpack.io/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.hexworks.zircon:zircon.core-jvm:2020.0.2-PREVIEW")
    implementation("org.hexworks.zircon:zircon.jvm.swing:2020.0.2-PREVIEW")
    testImplementation("junit", "junit", "4.12")
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
}
