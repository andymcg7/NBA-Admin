package com.andymcg.northumberlandbadmintonadmin

import com.andymcg.northumberlandbadmintonadmin.page.DashboardPage
import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.fluentlenium.configuration.ConfigurationProperties
import org.fluentlenium.configuration.FluentConfiguration
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.context.ActiveProfiles

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
//@DirtiesContext
abstract class AbstractFunctionalSpec : FluentTest() {

    @Page lateinit var dashboard: DashboardPage

    @Value("\${server.port}") lateinit var port: String

    @Value("\${spring.security.user.name}") private lateinit var username: String

    @Value("\${spring.security.user.password}") private lateinit var password: String

    @Autowired lateinit var ctx: GenericApplicationContext

    @Autowired lateinit var config: NorthumberlandBadmintonConfiguration

    fun superuserName(): String = username

    fun superuserPass(): String = password

    @BeforeEach
    fun baseSetup() {
        System.setProperty("webdriver.chrome.driver", "C://windows//chromedriver.exe")
    }

    override fun getBaseUrl(): String {
        return "http://localhost:$port"
    }
}