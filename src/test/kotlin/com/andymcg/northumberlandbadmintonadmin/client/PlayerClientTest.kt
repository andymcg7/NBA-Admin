package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomClubResource
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomPlayerResource
import com.andymcg.northumberlandbadmintonadmin.stub.*
import org.springframework.beans.factory.annotation.Autowired

class PlayerClientTest : AbstractClientTest<PlayerResource, PlayerClient>(::extractPlayerId) {

    @Autowired
    lateinit var theClient: PlayerClient

    @Autowired
    lateinit var theStubService: PlayerRestServiceStubService

    @Autowired
    lateinit var theClubStubService: ClubRestServiceStubService

    override fun getClient(): PlayerClient = theClient

    override fun getStubService(): AbstractRestServiceStubService<PlayerResource> =
        theStubService

    override fun randomResource(): PlayerResource {
        // bit of a hack caused by the lazy call to fetch clubs in the deserializer
        if (theClubStubService.findAll().isEmpty()) {
            (1..2).map { theClubStubService.save(generateRandomClubResource()) }
        }
        return generateRandomPlayerResource(mainClub = theClubStubService.findAll().first(),
            secondClub = theClubStubService.findAll().last())
    }

    override fun callConcreteFindAll(): List<PlayerResource> = getClient().findAll()

    override fun mutate(resource: PlayerResource): PlayerResource =
        resource.copy(name = TestUtils.randomAlphabetic(20))
}