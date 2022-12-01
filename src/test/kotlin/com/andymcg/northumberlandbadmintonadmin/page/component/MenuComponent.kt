package com.andymcg.northumberlandbadmintonadmin.page.component

import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.components.ComponentInstantiator
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class MenuComponent(
    el: WebElement,
    ctrl: FluentControl,
    instantiator: ComponentInstantiator
) {

    @FindBy(css = "li")
    lateinit var items: FluentList<MenuItemComponent>

    fun resourceItems(): List<String> = items.map { it.resourceItem() }

    fun clickToViewResourceItem(resourceName: String) {
        items.first { it.resourceItem() == resourceName }.click()
    }
}

class MenuItemComponent(
    el: WebElement,
    ctrl: FluentControl,
    instantiator: ComponentInstantiator
) : FluentWebElement(el, ctrl, instantiator) {

    fun toggle() {
        val active = isActive()
        click()
        await().until{ isActive() != active }
    }
    fun isActive(): Boolean = attribute("class").split(" ").contains("active")

    fun setActive() {
        if (!isActive()) toggle()
    }

    fun resourceItem(): String = attribute("data-resource")
}