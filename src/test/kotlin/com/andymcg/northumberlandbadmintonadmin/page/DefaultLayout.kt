package com.andymcg.northumberlandbadmintonadmin.page

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

open class DefaultLayout<P : DefaultLayout<P>>(private val clazz: Class<P>) : FluentPage() {

    @FindBy(css = ".nav") lateinit var nav: FluentWebElement

    @FindBy(css = ".form-login") lateinit var loginForm: FluentWebElement

    fun<
        RES,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>
    > clickToViewAll(resourceName: String, listPageClass: Class<LIST>): LIST {
//        nav.clickToViewAll
        return newInstance(listPageClass)
    }
}