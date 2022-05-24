package com.andymcg.northumberlandbadmintonadmin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.net.URL
import javax.annotation.PostConstruct


@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration
class NorthumberlandBadmintonAdminApplication {
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			SpringApplication.run(NorthumberlandBadmintonAdminApplication::class.java, *args)
		}

		val logger by LoggerDelegate()
	}

	@Autowired
	lateinit var env: Environment

	@PostConstruct
	fun init() {
		//			fixie:qAVBUcybILFYwxk@speedway.usefixie.com:1080
		if (isProfileActive("heroku", env.activeProfiles.toList())) {
			val fixie = System.getenv("FIXIE_SOCKS_HOST")
			val parts = fixie.split("@")
			val fixieUserInfo: List<String> = parts[0].split(':')
			val fixieUser = fixieUserInfo[0]
			val fixiePassword = fixieUserInfo[1]
			val fixieProxy = parts[1].split(":")
			System.setProperty("socksProxyHost", fixieProxy[0])
			System.setProperty("socksProxyPort", fixieProxy[1])
			logger.info("fixieProxy: $fixieProxy[0]")
			logger.info("fixiePport: $fixieProxy[1]")
			Authenticator.setDefault(ProxyAuthenticator(fixieUser, fixiePassword))
		}
	}

	private fun isProfileActive(profile: String, profiles: List<String>): Boolean =
		profiles.contains(profile)
}

object Uris {
	const val root = ""
}

class ProxyAuthenticator constructor(user: String, password: String) : Authenticator() {
	private val passwordAuthentication: PasswordAuthentication
	override fun getPasswordAuthentication(): PasswordAuthentication {
		return passwordAuthentication
	}

	init {
		passwordAuthentication = PasswordAuthentication(user, password.toCharArray())
	}
}