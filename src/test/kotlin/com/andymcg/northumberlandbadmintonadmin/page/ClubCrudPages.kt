package com.andymcg.northumberlandbadmintonadmin.page

import com.andymcg.northumberlandbadmintonadmin.club.Club

class ClubListPage :
        AbstractResourceListPage<
                Club, ClubListPage, ClubCreatePage, ClubEditPage>(
            ClubListPage::class.java,
            ClubCreatePage::class.java,
            ClubEditPage::class.java
                ) {

                }

class ClubCreatePage :
    AbstractResourceCreatePage<
            Club, ClubListPage, ClubCreatePage, ClubEditPage>(
        ClubCreatePage::class.java
    ) {
    override fun fillInAndSave(resource: Club): ClubListPage {
        TODO("Not yet implemented")
    }

}

class ClubEditPage :
    AbstractResourceEditPage<
            Club, ClubListPage, ClubCreatePage, ClubEditPage>(
        ClubEditPage::class.java
    ) {
    override fun fillInAndSave(resource: Club): ClubListPage {
        TODO("Not yet implemented")
    }

    override fun thenDisplays(resource: Club, baseUrl: String): ClubEditPage {
        TODO("Not yet implemented")
    }

}