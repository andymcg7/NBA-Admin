package com.andymcg.northumberlandbadmintonadmin.stub

import com.andymcg.northumberlandbadmintonadmin.client.TeamResource
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

fun extractTeamId(resource: TeamResource): Long? = resource.id

fun setTeamId(resource: TeamResource, id: Long): TeamResource = resource.copy(id = id)

@RestController
@RequestMapping("/stubs/nba-service/api/v1/teams")
@Profile("functional-spec", "api-spec", "local")
class TeamRestServiceStub(repository: TeamRestServiceStubService) :
AbstractJpaRestServiceStub<TeamResource, TeamRestServiceStubService>(
    TeamResource::class.java, repository, "teams", ::extractTeamId
)

@Service
@Profile("functional-spec", "api-spec", "local")
class TeamRestServiceStubService : AbstractRestServiceStubService<TeamResource>(
    TeamResource::class.java, ::extractTeamId, ::setTeamId
)