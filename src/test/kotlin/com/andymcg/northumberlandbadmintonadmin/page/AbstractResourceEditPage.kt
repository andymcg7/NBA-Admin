package com.andymcg.northumberlandbadmintonadmin.page

abstract class AbstractResourceEditPage<
        RES,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(pageClass: Class<EDIT>) :
    DefaultLayout<EDIT>(pageClass) {

        abstract fun fillInAndSave(resource: RES): LIST
        abstract fun thenDisplays(resource: RES, baseUrl: String = ""): EDIT
}