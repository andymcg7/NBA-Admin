package com.andymcg.northumberlandbadmintonadmin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.net.URL


@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration
class NorthumberlandBadmintonAdminApplication {
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			SpringApplication.run(NorthumberlandBadmintonAdminApplication::class.java, *args)
			val fixie = URL(System.getenv("FIXIE_SOCKS_HOST"))
			val fixieUserInfo: List<String> = fixie.userInfo.split(':')
			val fixieUser = fixieUserInfo[0]
			val fixiePassword = fixieUserInfo[1]
			System.setProperty("socksProxyHost", fixie.host)
			Authenticator.setDefault(ProxyAuthenticator(fixieUser, fixiePassword))
		}
	}
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