package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.stub.AbstractRestServiceStubService
import org.assertj.core.api.BDDAssertions.then
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.support.FindBy

abstract class AbstractResourceListPage<
        RES,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(
    pageClazz: Class<LIST>,
    val createPageClass: Class<CREATE>,
    val editPageClass: Class<EDIT>
        ) : DefaultLayout<LIST>(pageClazz) {

    @FindBy(css = ".create-button") lateinit var createButton: FluentWebElement

    @FindBy(css = ".resource") lateinit var resources: FluentList<FluentWebElement>

    abstract fun thenDisplays(resources: List<RES>): LIST

    open fun thenPersisted(
        expected: RES,
        id: Long,
        stub: AbstractRestServiceStubService<RES>,
        args: Array<Any> = emptyArray()
    ): LIST {
        val actual = stub.findById(id)
        then(actual).isEqualTo(expected)
        return thisPage()
    }

    fun clickToCreateNew(): CREATE {
        createButton.click()
        return newInstance(createPageClass)
    }

    fun clickToEdit(resource: RES): EDIT {
        val row = findResourceRow(resource)
        row.el(".edit-button").click()
        return newInstance(editPageClass)
    }

    fun clickToDelete(resource: RES): LIST {
        val row = findResourceRow(resource)
        row.el(".delete-button").click()
        return thisPage()
    }

    abstract fun resourceId(resource: RES): Long

    private fun findResourceRow(resource: RES): FluentWebElement {
        val id = resourceId(resource)
        return resources.first { it.id().toLong() == id }
    }
}