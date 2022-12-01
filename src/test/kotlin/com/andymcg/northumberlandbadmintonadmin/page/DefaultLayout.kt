package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.page.component.LoginFormComponent
import com.andymcg.northumberlandbadmintonadmin.page.component.MenuComponent
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

open class DefaultLayout<P : DefaultLayout<P>>(private val clazz: Class<P>) : FluentPage() {

    @FindBy(css = ".nav-wrapper")
    lateinit var nav: MenuComponent

    @FindBy(css = ".form-signin")
    lateinit var loginForm: LoginFormComponent

    fun <
            RES,
            LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
            CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
            EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>
            clickToViewAll(resourceName: String, listPageClass: Class<LIST>): LIST {
        nav.clickToViewResourceItem(resourceName)
        return newInstance(listPageClass)
    }

    fun login(username: String, password: String): P {
        loginForm.login(username, password)
        return thisPage()
    }

    protected fun thisPage(): P = newInstance(clazz)
}