package com.andymcg.northumberlandbadmintonadmin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties
@EnableAutoConfiguration
class NorthumberlandBadmintonAdminApplication {
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			SpringApplication.run(NorthumberlandBadmintonAdminApplication::class.java, *args)
		}
	}
}

object Uris {
	const val root = ""
}