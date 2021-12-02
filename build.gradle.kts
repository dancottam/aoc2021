plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks {
    /*sourceSets {
        main {
            java.srcDirs("src")
        }
    }*/

    wrapper {
        gradleVersion = "7.3"
    }
}

tasks.test {
    useJUnitPlatform()
}
