package com.andymcg.northumberlandbadmintonadmin.stub

import com.andymcg.northumberlandbadmintonadmin.client.PlayerResource
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

fun extractPlayerId(resource: PlayerResource): Long? = resource.id

fun setPlayerId(resource: PlayerResource, id: Long): PlayerResource = resource.copy(id = id)

@RestController
@RequestMapping("/stubs/nba-service/api/v1/players")
@Profile("functional-spec", "api-spec", "local")
class PlayerRestServiceStub(repository: PlayerRestServiceStubService) :
    AbstractJpaRestServiceStub<PlayerResource, PlayerRestServiceStubService>(
    PlayerResource::class.java, repository, "players", ::extractPlayerId
)

@Service
@Profile("functional-spec", "api-spec", "local")
class PlayerRestServiceStubService : AbstractRestServiceStubService<PlayerResource>(
    PlayerResource::class.java, ::extractPlayerId, ::setPlayerId
)