package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaEntity

abstract class AbstractResourceListPage<
        RES : AbstractJpaEntity,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(
    pageClazz: Class<LIST>,
    val createPageClass: Class<CREATE>,
    val editPageClass: Class<EDIT>
        ) : DefaultLayout<LIST>(pageClazz) {
}