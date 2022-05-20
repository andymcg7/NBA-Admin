package com.andymcg.NorthumberlandBadmintonAdmin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NorthumberlandBadmintonAdminApplication {
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			SpringApplication.run(NorthumberlandBadmintonAdminApplication::class.java, *args)
		}
	}
}
