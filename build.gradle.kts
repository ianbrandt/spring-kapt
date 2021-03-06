import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel.CURRENT
import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.5.30"

	id("org.springframework.boot") version "2.5.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.github.ben-manes.versions") version "0.39.0"

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("kapt") version kotlinVersion
}

group = "com.ianbrandt"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	kapt("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "11"
		}
	}

	withType<Test> {
		useJUnitPlatform()
	}

	withType<DependencyUpdatesTask> {
		rejectVersionIf {
			isNonStable(candidate.version)
		}
		gradleReleaseChannel = CURRENT.id
	}

	named<Wrapper>("wrapper") {
		gradleVersion = "7.2"
		distributionType = ALL
	}
}

fun isNonStable(version: String): Boolean {
	val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
		version.toUpperCase()
			.contains(it)
	}
	val unstableKeyword =
		listOf("""M\d+""").any { version.toUpperCase().contains(it.toRegex()) }
	val regex = "^[0-9,.v-]+(-r)?$".toRegex()
	val isStable = (stableKeyword && !unstableKeyword) || regex.matches(version)
	return isStable.not()
}
