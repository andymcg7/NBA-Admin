package com.andymcg.northumberlandbadmintonadmin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc


@Rollback
@SpringBootTest
@ActiveProfiles("api-spec")
class AbstractApiTest {

@Autowired lateinit var ctx: GenericApplicationContext

}