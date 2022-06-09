package com.andymcg.northumberlandbadmintonadmin.stub

import com.andymcg.northumberlandbadmintonadmin.client.ClubResource
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

fun extractClubId(resource: ClubResource): Long? = resource.id

fun setClubId(resource: ClubResource, id: Long): ClubResource = resource.copy(id = id)

@RestController
@RequestMapping("/stubs/nba-service/api/v1/clubs")
@Profile("functional-spec", "api-spec", "local")
class ClubRestServiceStub(repository: ClubRestServiceStubService) :
AbstractJpaRestServiceStub<ClubResource, ClubRestServiceStubService>(
    ClubResource::class.java, repository, "clubs", ::extractClubId
)

@Service
@Profile("functional-spec", "api-spec", "local")
class ClubRestServiceStubService : AbstractRestServiceStubService<ClubResource>(
    ClubResource::class.java, ::extractClubId, ::setClubId
)