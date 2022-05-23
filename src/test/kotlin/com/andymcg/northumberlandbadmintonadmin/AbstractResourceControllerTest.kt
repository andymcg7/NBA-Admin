package com.andymcg.northumberlandbadmintonadmin

import com.andymcg.northumberlandbadmintonadmin.page.AbstractResourceCreatePage
import com.andymcg.northumberlandbadmintonadmin.page.AbstractResourceEditPage
import com.andymcg.northumberlandbadmintonadmin.page.AbstractResourceListPage
import org.junit.jupiter.api.Test

abstract class AbstractResourceControllerTest<
        RES : AbstractJpaEntity,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(
    val resourceName: String,
    val listPageClass: Class<LIST>
        ) : AbstractFunctionalSpec() {

//    abstract fun randomValidResource(): RES
//
//    abstract fun updatedCopy(resource: RES): RES
//
//    fun prepopulatedResources(): List<RES> = emptyList()
//
//    abstract fun persistedValidEntityId(): Long
//
//    @Test
//    fun `should create, read, update and delete resource`() {
//        // given
//
//
//        // when
//
//        // then
//    }

}