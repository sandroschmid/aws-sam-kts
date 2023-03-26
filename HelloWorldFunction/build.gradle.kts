plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.graalvm.buildtools.native")
    application
}

val javaVersion = JavaLanguageVersion.of(17)
kotlin {
    jvmToolchain {
        languageVersion.set(javaVersion)
    }
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.amazonaws.services.lambda.runtime.api.client.AWSLambda")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
    implementation("com.amazonaws:aws-lambda-java-runtime-interface-client:2.3.1")

    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
}

graalvmNative {
    binaries {
        named("main") {
            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(javaVersion)
//                vendor.set(JvmVendorSpec.matching("GraalVM Community"))
            })
            imageName.set("native")
            verbose.set(true)
            fallback.set(false)
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
