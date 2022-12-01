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
}


group = "com.andymcg"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.apache.commons:commons-lang3")

	implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.5.3")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.fluentlenium:fluentlenium-junit-jupiter:5.0.4")
	testImplementation("org.fluentlenium:fluentlenium-assertj:5.0.4")
	testImplementation("com.thedeanda:lorem:2.1")
	testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
	testImplementation("io.rest-assured:spring-mock-mvc:4.3.3")
	testImplementation("net.sourceforge.htmlunit:htmlunit:2.38.0")
	testImplementation("org.seleniumhq.selenium:selenium-chrome-driver:4.6.0")

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
