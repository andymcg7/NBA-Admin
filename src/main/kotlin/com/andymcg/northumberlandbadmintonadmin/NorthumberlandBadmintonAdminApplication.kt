package com.andymcg.northumberlandbadmintonadmin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class NorthumberlandBadmintonAdminApplication {
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			SpringApplication.run(NorthumberlandBadmintonAdminApplication::class.java, *args)
		}
	}
}
