package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaEntity

abstract class AbstractResourceCreatePage<
        RES : AbstractJpaEntity,
        LIST : AbstractResourceListPage<RES, LIST, CREATE, EDIT>,
        CREATE : AbstractResourceCreatePage<RES, LIST, CREATE, EDIT>,
        EDIT : AbstractResourceEditPage<RES, LIST, CREATE, EDIT>>(pageClass: Class<CREATE>) :
DefaultLayout<CREATE>(pageClass) {

    abstract fun fillInAndSave(resource: RES): LIST
}