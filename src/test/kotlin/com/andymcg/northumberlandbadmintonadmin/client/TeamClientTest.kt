package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomClubResource
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomTeamResource
import com.andymcg.northumberlandbadmintonadmin.stub.AbstractRestServiceStubService
import com.andymcg.northumberlandbadmintonadmin.stub.ClubRestServiceStubService
import com.andymcg.northumberlandbadmintonadmin.stub.TeamRestServiceStubService
import com.andymcg.northumberlandbadmintonadmin.stub.extractTeamId
import org.springframework.beans.factory.annotation.Autowired

class TeamClientTest : AbstractClientTest<TeamResource, TeamClient>(::extractTeamId) {

    @Autowired lateinit var theClient: TeamClient

    @Autowired lateinit var theStubService: TeamRestServiceStubService

    @Autowired lateinit var theClubStubService: ClubRestServiceStubService

    override fun getClient(): TeamClient = theClient

    override fun getStubService(): AbstractRestServiceStubService<TeamResource> =
        theStubService

    override fun randomResource(): TeamResource {
        val club = theClubStubService.save(generateRandomClubResource())
        return generateRandomTeamResource(club = club)
    }

    override fun callConcreteFindAll(): List<TeamResource> = getClient().findAll()

    override fun mutate(resource: TeamResource): TeamResource =
        resource.copy(teamName = TestUtils.randomAlphabetic(20))
}