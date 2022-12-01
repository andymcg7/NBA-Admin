package com.andymcg.northumberlandbadmintonadmin

import org.fluentlenium.configuration.ConfigurationDefaults
import org.openqa.selenium.WebDriver
import java.util.concurrent.TimeUnit


class FluentLeniumConfig : ConfigurationDefaults() {

    override fun getWebDriver(): String {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe")
//        val driver: WebDriver = ChromeDriver()
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        return "chrome"
    }
}