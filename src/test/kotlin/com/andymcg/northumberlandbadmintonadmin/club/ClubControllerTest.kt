package com.andymcg.northumberlandbadmintonadmin.club

import com.andymcg.northumberlandbadmintonadmin.AbstractResourceControllerTest
import com.andymcg.northumberlandbadmintonadmin.page.ClubCreatePage
import com.andymcg.northumberlandbadmintonadmin.page.ClubEditPage
import com.andymcg.northumberlandbadmintonadmin.page.ClubListPage
import org.junit.jupiter.api.Disabled

@Disabled
class ClubControllerTest :
        AbstractResourceControllerTest<
                Club, ClubListPage, ClubCreatePage, ClubEditPage>(
            "clubs", ClubListPage::class.java
                ) {
}
