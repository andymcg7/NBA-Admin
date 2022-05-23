package com.andymcg.northumberlandbadmintonadmin.team

import com.andymcg.northumberlandbadmintonadmin.AbstractResourceControllerTest
import com.andymcg.northumberlandbadmintonadmin.page.TeamCreatePage
import com.andymcg.northumberlandbadmintonadmin.page.TeamEditPage
import com.andymcg.northumberlandbadmintonadmin.page.TeamListPage

class TeamControllerTest :
    AbstractResourceControllerTest<
            Team, TeamListPage, TeamCreatePage, TeamEditPage> (
        "teams", TeamListPage::class.java
    ) {
}