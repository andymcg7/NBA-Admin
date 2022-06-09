package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.stub.*
import org.springframework.beans.factory.annotation.Autowired

class PlayerClientTest : AbstractClientTest<PlayerResource, PlayerClient>(::extractPlayerId) {

    @Autowired
    lateinit var theClient: PlayerClient

    @Autowired
    lateinit var theStubService: PlayerRestServiceStubService

    override fun getClient(): PlayerClient = theClient

    override fun getStubService(): AbstractRestServiceStubService<PlayerResource> =
        theStubService

    override fun randomResource(): PlayerResource = TestUtils.generateRandomPlayerResource()

    override fun callConcreteFindAll(): List<PlayerResource> = getClient().findAll()

    override fun mutate(resource: PlayerResource): PlayerResource =
        resource.copy(name = TestUtils.randomAlphabetic(20))
}