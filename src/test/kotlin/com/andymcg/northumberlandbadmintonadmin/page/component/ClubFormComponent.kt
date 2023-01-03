package com.andymcg.northumberlandbadmintonadmin.page.component

import com.andymcg.northumberlandbadmintonadmin.client.ClubResource
import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.components.ComponentInstantiator
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

data class EditedClubResource(
    val id: Long,
    val name: String,
    val venue: String
)

fun ClubResource.toEditedClubResource(): EditedClubResource =
    EditedClubResource(id!!, name ?: "", venue ?: "")

class ClubFormComponent(
    el: WebElement,
    control: FluentControl,
    instantiator: ComponentInstantiator
) : FluentWebElement(el, control, instantiator) {

    @FindBy(name = "id") lateinit var id: FluentWebElement
    @FindBy(name = "name") lateinit var name: FluentWebElement
    @FindBy(name = "venue") lateinit var venue: FluentWebElement

    @FindBy(css = "button[type='submit']") lateinit var save: FluentWebElement

    fun fillInAndSave(resource: ClubResource) {
        name.fill().with(resource.name ?: "")
        venue.fill().with(resource.venue ?: "")
        save.click()
    }

    fun toEditedClubResource(): EditedClubResource =
        EditedClubResource(
            id = id.value().toLong(),
            name = name.value(),
            venue = venue.value()
        )
}