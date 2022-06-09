package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.NorthumberlandBadmintonConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class ClubClient(config: NorthumberlandBadmintonConfiguration, restTemplateBuilder: RestTemplateBuilder) : AbstractClient<ClubResource>(
    config, restTemplateBuilder, ClubResource::class.java, "clubs"
) {

    private val listResponseType = object : ParameterizedTypeReference<List<ClubResource>>() {}

    override fun findAll(): List<ClubResource> = findAll(listResponseType)


}