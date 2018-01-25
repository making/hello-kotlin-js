import com.moowork.gradle.node.npm.NpmTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    id("com.moowork.node") version "1.2.0"
}

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.0")
    }
}

apply {
    plugin("kotlin-platform-js")
    plugin("kotlin-dce-js")
}

repositories {
    mavenCentral()
}

node {
    download = true
}

dependencies {
    "compile"("org.jetbrains.kotlin:kotlin-stdlib-js")
}

tasks.withType<Kotlin2JsCompile> {
    kotlinOptions {
        moduleKind = "commonjs"
    }
}

task<NpmTask>("buildBundle") {
    dependsOn("npmInstall", "runDceKotlinJs")
    inputs.dir("src/main/kotlin")
    inputs.dir("src/main/web")
    outputs.dir("build/web")
    val command = if (project.hasProperty("prod")) "bundle-prod" else "bundle"
    setArgs(listOf("run", command))
    doLast {
        copy {
            from("build/web")
            into("./build/resources/main/static")
        }
        copy {
            from("build/web")
            into("./out/production/resources/static")
        }
    }
}

tasks.getByName("build").dependsOn("buildBundle")
