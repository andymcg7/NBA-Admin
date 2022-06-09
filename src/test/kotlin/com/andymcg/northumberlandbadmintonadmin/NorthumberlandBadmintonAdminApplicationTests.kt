package com.andymcg.northumberlandbadmintonadmin

import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NorthumberlandBadmintonAdminApplicationTests : AbstractApiTest() {

	@Test
	fun contextLoads() {
		BDDAssertions.then(ctx.isRunning).isTrue
	}
}