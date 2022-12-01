package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.Uris
import org.assertj.core.api.BDDAssertions.then
import org.fluentlenium.core.annotation.PageUrl
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

@PageUrl(Uris.dashboard)
class DashboardPage : DefaultLayout<DashboardPage>(DashboardPage::class.java) {

    @FindBy(css = "h2") lateinit var heading: FluentWebElement

    fun thenDisplays(): DashboardPage {
        then(heading.text().trim()).isEqualTo("Main dashboard")
        return thisPage()
    }
}