import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.diffplug.spotless") version "5.13.0"
	id("com.adarshr.test-logger") version "3.0.0"
	id("org.owasp.dependencycheck") version "6.2.2"
	id("com.github.ben-manes.versions") version "0.39.0"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.allopen") version "1.5.20"
	kotlin("plugin.jpa") version "1.5.20"
	kotlin("plugin.noarg") version "1.5.20"
}

group = "com.andymcg"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.apache.commons:commons-lang3")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")

	runtimeOnly("mysql:mysql-connector-java")
	runtimeOnly("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.5.3")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.zonky.test:embedded-database-spring-test:1.6.3")
	testImplementation("org.fluentlenium:fluentlenium-junit-jupiter:5.0.1")
	testImplementation("org.fluentlenium:fluentlenium-assertj:5.0.1")
	testImplementation("com.thedeanda:lorem:2.1")
	testImplementation("org.testcontainers:mysql:1.16.2")
	testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
	testImplementation("io.rest-assured:spring-mock-mvc:4.3.3")
	testImplementation("com.playtika.testcontainers:embedded-redis:2.0.9")
	testImplementation("org.testcontainers:junit-jupiter:1.16.2")
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

noArg {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
	enabled = false
	manifest {
		attributes["Main-Class"] = "com.andymcg.northumberlandbadmintonadmin.NorthumberlandBadmintonAdminApplication"
	}
}