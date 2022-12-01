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
}

object Uris {
	const val root = ""
	const val dashboard = "$root/"
}


enum class StaticResourceLocations(val antMatcher: String) {
	RESOURCES("/resources/**"),
	WEBJARS("/webjars/**");

	companion object {
		fun patterns(): Array<String> = values().map() { it.antMatcher }.toTypedArray()
	}
}