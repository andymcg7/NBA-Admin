package com.andymcg.northumberlandbadmintonadmin.page

abstract class AbstractResourceListPage<
        RES,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(
    pageClazz: Class<LIST>,
    val createPageClass: Class<CREATE>,
    val editPageClass: Class<EDIT>
        ) : DefaultLayout<LIST>(pageClazz) {
}