package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.client.ClubResource
import com.andymcg.northumberlandbadmintonadmin.page.component.ClubFormComponent
import com.andymcg.northumberlandbadmintonadmin.page.component.toEditedClubResource
import org.assertj.core.api.BDDAssertions.then
import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.components.ComponentInstantiator
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

data class ListedClub(
    val name: String,
    val venue: String
)

fun ClubResource.toListedClub(): ListedClub =
    ListedClub(name ?: "", venue ?: "")

class ListedClubComponent(
    el: WebElement,
    ctrl: FluentControl,
    instantiatior: ComponentInstantiator
) : FluentWebElement(el, ctrl, instantiatior) {

    @FindBy(css = ".name") lateinit var name: FluentWebElement

    @FindBy(css = ".venue") lateinit var venue: FluentWebElement

    fun toListedClub(): ListedClub =
        ListedClub(name = name.text().trim(),
            venue = venue.text().trim())

}

class ClubListPage :
        AbstractResourceListPage<
                ClubResource, ClubListPage, ClubCreatePage, ClubEditPage>(
            ClubListPage::class.java,
            ClubCreatePage::class.java,
            ClubEditPage::class.java
                ) {

    override fun thenDisplays(resources: List<ClubResource>): ClubListPage {
        val actual = this.resources.`as`(ListedClubComponent::class.java).map { it.toListedClub() }
        val expected = resources.map { it.toListedClub() }
        then(actual).containsExactlyElementsOf(expected)
        return thisPage()
    }

    override fun resourceId(resource: ClubResource): Long = resource.id!!
}

class ClubCreatePage :
    AbstractResourceCreatePage<
            ClubResource, ClubListPage, ClubCreatePage, ClubEditPage>(
        ClubCreatePage::class.java
    ) {

    @FindBy(css = "form.club") lateinit var form: ClubFormComponent

    override fun fillInAndSave(resource: ClubResource): ClubListPage {
        form.fillInAndSave(resource)
        return newInstance(ClubListPage::class.java)
    }

}

class ClubEditPage :
    AbstractResourceEditPage<
            ClubResource, ClubListPage, ClubCreatePage, ClubEditPage>(
        ClubEditPage::class.java
    ) {

    @FindBy(css = "form.club") lateinit var form: ClubFormComponent

    override fun fillInAndSave(resource: ClubResource): ClubListPage {
        form.fillInAndSave(resource)
        return newInstance(ClubListPage::class.java)
    }

    override fun thenDisplays(resource: ClubResource, baseUrl: String): ClubEditPage {
        then(form.toEditedClubResource()).isEqualTo(resource.toEditedClubResource())
        return thisPage()
    }

}

