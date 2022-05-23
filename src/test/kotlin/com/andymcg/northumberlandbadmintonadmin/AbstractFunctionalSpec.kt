package com.andymcg.northumberlandbadmintonadmin

import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.fluentlenium.configuration.FluentConfiguration
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.core.annotation.Page
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

private const val FLUENT_CAPABILITIES =
    """
{
    "chromeOptions": {
            "args": [
                "headless",
                "window-size=1600,1024",
                "disable-gpu"
            ]
    }
}
"""

@AutoConfigureMockMvc
@ActiveProfiles("functional-spec")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FluentConfiguration(
    capabilities = FLUENT_CAPABILITIES,
    implicitlyWait = 1000,
    screenshotMode = ConfigurationProperties.TriggerMode.AUTOMATIC_ON_FAIL,
    screenshotPath = "build/reports/tests/test/screenshots"
)
@AutoConfigureTestEntityManager
@Testcontainers
@DirtiesContext
abstract class AbstractFunctionalSpec : FluentTest() {

    companion object {
        @Container
        private val mysqlContainer = KotlinMySQLContainer(image = "mysql:8.0.22")
            .withDatabaseName("nba")
            .withUrlParam("sessionVariables", "sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
        }
    }

//    @Page lateinit var dashboard: DashboadPage

    @Value("\${server.port}") lateinit var port: String

    @Autowired lateinit var ctx: GenericApplicationContext

    override fun getBaseUrl(): String {
        return "http://localhost:$port"
    }
}