package com.andymcg.northumberlandbadmintonadmin.page.component

import org.fluentlenium.core.FluentControl
import org.fluentlenium.core.components.ComponentInstantiator
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class LoginFormComponent(
    el: WebElement,
    control: FluentControl,
    instantiator: ComponentInstantiator
) : FluentWebElement(el, control, instantiator) {

    @FindBy(name = "username") private lateinit var usernameInput: FluentWebElement
    @FindBy(name = "password") private lateinit var passwordInput: FluentWebElement

    fun login(username: String, password: String) {
        usernameInput.fill().with(username)
        passwordInput.fill().with(password)
        submit()
    }

}