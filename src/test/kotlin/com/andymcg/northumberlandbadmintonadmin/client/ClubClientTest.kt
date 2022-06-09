package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomClubResource
import com.andymcg.northumberlandbadmintonadmin.stub.AbstractRestServiceStubService
import com.andymcg.northumberlandbadmintonadmin.stub.ClubRestServiceStubService
import com.andymcg.northumberlandbadmintonadmin.stub.extractClubId
import org.springframework.beans.factory.annotation.Autowired

class ClubClientTest : AbstractClientTest<ClubResource, ClubClient>(::extractClubId) {

    @Autowired lateinit var theClient: ClubClient

    @Autowired lateinit var theStubService: ClubRestServiceStubService

    override fun getClient(): ClubClient = theClient

    override fun getStubService(): AbstractRestServiceStubService<ClubResource> =
        theStubService

    override fun randomResource(): ClubResource = generateRandomClubResource()

    override fun callConcreteFindAll(): List<ClubResource> = getClient().findAll()

    override fun mutate(resource: ClubResource): ClubResource =
        resource.copy(name = TestUtils.randomAlphabetic(20))
}