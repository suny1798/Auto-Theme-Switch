plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.sph"
version = "1.1.0"

repositories {
    //    mavenCentral()
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    localPath.set("D:\\Code\\Tools\\IntelliJ IDEA 2024.3.7")
    //    version.set("2023.2.6")
    //    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))

    // updateSinceUntilBuild.set(true) 表示插件会自动更新 plugin.xml 中的 since-build 和 until-build 属性
    // 设置为 true 时，Gradle 插件会根据 IntelliJ 平台版本自动设置构建范围
    // 这有助于确保插件与不同版本的 IDE 兼容
    updateSinceUntilBuild.set(true)
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("243")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
        channels = listOf("beta")
    }
}
