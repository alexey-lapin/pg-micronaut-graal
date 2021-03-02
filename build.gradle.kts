plugins {
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.3.4"
}

repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("pg.micronaut.graal.*")
    }
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.18")
    compileOnly("org.projectlombok:lombok:1.18.18")

    annotationProcessor("io.micronaut:micronaut-inject-java")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")

    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.liquibase:micronaut-liquibase")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("org.slf4j:jcl-over-slf4j:1.7.30")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.h2database:h2")
}


application {
    mainClass.set("pg.micronaut.graal.Application")
}

java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
    targetCompatibility = JavaVersion.toVersion("1.8")
}

tasks {
//    withType<Exec> {
//        doFirst{println("b " + commandLine)}
//        doLast{println("a " + commandLine)}
//    }

}

