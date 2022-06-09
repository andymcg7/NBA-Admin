package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.NorthumberlandBadmintonConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class TeamClient(config: NorthumberlandBadmintonConfiguration, restTemplateBuilder: RestTemplateBuilder) : AbstractClient<TeamResource>(
    config, restTemplateBuilder, TeamResource::class.java, "teams.php"
) {

    private val listResponseType = object : ParameterizedTypeReference<List<TeamResource>>() {}

    override fun findAll(): List<TeamResource> = findAll(listResponseType)
}