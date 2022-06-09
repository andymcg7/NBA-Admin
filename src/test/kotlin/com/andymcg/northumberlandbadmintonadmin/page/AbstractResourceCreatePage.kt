package com.andymcg.northumberlandbadmintonadmin.page

abstract class AbstractResourceCreatePage<
        RES,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(pageClass: Class<CREATE>) :
DefaultLayout<CREATE>(pageClass) {

    abstract fun fillInAndSave(resource: RES): LIST
}