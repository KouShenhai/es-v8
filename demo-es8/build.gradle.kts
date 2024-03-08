plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "org.laokoutech"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
    maven { setUrl("https://plugins.gradle.org/m2/") }
    maven { setUrl("https://repo.spring.io/release") }
    maven { setUrl("https://maven.aliyun.com/repository/public") }
    maven {
        setUrl("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    //implementation("org.springframework.data","spring-data-elasticsearch","5.2.3")
    implementation("co.elastic.clients:elasticsearch-java:8.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")

    implementation("org.projectlombok","lombok","1.18.30")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

