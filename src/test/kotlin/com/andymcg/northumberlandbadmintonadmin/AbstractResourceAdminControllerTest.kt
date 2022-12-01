package com.andymcg.northumberlandbadmintonadmin

import com.andymcg.northumberlandbadmintonadmin.page.AbstractResourceCreatePage
import com.andymcg.northumberlandbadmintonadmin.page.AbstractResourceEditPage
import com.andymcg.northumberlandbadmintonadmin.page.AbstractResourceListPage
import com.andymcg.northumberlandbadmintonadmin.page.DashboardPage
import com.andymcg.northumberlandbadmintonadmin.stub.AbstractRestServiceStubService
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test

abstract class AbstractResourceAdminControllerSpec<
    RES,
    LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
    CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
    EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>
(
    val resourceName: String,
    val listPageClass: Class<LIST>,
    val stub: AbstractRestServiceStubService<RES>
) : AbstractFunctionalSpec()  {

    @Page lateinit var dashboardPage: DashboardPage

    @Test
    fun `should create, read, update, and delete resource`() {
        // given
        val existing = prepopulatedResources()
        val id = existing.size + 1L
        val resource = randomValidResource(id)
        val updated = updatedCopy(resource)

        // when...then
        dashboardPage
            .go<DashboardPage>()
            .login(superuserName(), superuserPass())
            .thenDisplays()
            .clickToViewAll(resourceName, listPageClass)
    }

    abstract fun randomValidResource(id: Long): RES

    abstract fun updatedCopy(resource: RES): RES

    fun prepopulatedResources(): List<RES> = emptyList()

    abstract fun persistedValidEntityId(): Long

}

annotation class RestResourceName(val value: String)