package com.andymcg.northumberlandbadmintonadmin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.EnvironmentAware
import org.springframework.core.env.Environment
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.net.URL
import javax.annotation.PostConstruct


@SpringBootApplication
@EnableConfigurationProperties(NorthumberlandBadmintonConfiguration::class)
@EnableAutoConfiguration
class NorthumberlandBadmintonAdminApplication : WebSecurityConfigurerAdapter() {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<NorthumberlandBadmintonAdminApplication>()
		}

		val logger by LoggerDelegate()
	}

	@Autowired
	lateinit var env: Environment

	override fun configure(web: WebSecurity) {
		web.ignoring().antMatchers("/stubs/**")
	}

	override fun configure(http: HttpSecurity) {
		http.authorizeRequests()
			.antMatchers(*StaticResourceLocations.patterns())
			.permitAll()
			.requestMatchers(EndpointRequest.to(HealthEndpoint::class.java))
			.permitAll()
			.and()
			.authorizeRequests()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.and()
			.httpBasic()
			.realmName("Northumberland Badminton Admin")
			.and()
			.csrf()
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()
			.referrerPolicy()
			.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE)
			.and()
			.contentSecurityPolicy(
				"Content-Security-Policy: default-src 'self'; font-src 'self' fonts.gstatic.com"
			)
	}


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
			logger.info("fixieProxy: ${fixieProxy.get(0)}")
			logger.info("fixiePport: ${fixieProxy.get(1)}")
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

enum class StaticResourceLocations(val antMatcher: String) {
	RESOURCES("/resources/**"),
	WEBJARS("/webjars/**");

	companion object {
		fun patterns(): Array<String> = values().map() { it.antMatcher }.toTypedArray()
	}
}