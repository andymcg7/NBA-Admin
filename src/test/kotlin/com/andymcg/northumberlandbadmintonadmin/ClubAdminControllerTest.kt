package com.andymcg.northumberlandbadmintonadmin

import com.andymcg.northumberlandbadmintonadmin.client.ClubResource
import com.andymcg.northumberlandbadmintonadmin.page.ClubCreatePage
import com.andymcg.northumberlandbadmintonadmin.page.ClubEditPage
import com.andymcg.northumberlandbadmintonadmin.page.ClubListPage
import com.andymcg.northumberlandbadmintonadmin.stub.ClubRestServiceStubService
import org.springframework.beans.factory.annotation.Autowired

class ClubAdminControllerTest
@Autowired constructor(stub: ClubRestServiceStubService) :
    AbstractResourceAdminControllerSpec<
    ClubResource, ClubListPage, ClubCreatePage, ClubEditPage>(
        "clubs", ClubListPage::class.java, stub
    ){

    override fun randomValidResource(id: Long): ClubResource = ClubResource(id = id)

    override fun updatedCopy(resource: ClubResource): ClubResource =
        resource.copy(
            name = TestUtils.randomWords(2),
            venue = TestUtils.randomWords(5)
        )

    override fun persistedValidEntityId(): Long = stub.save(TestUtils.generateRandomClubResource()).id!!
}