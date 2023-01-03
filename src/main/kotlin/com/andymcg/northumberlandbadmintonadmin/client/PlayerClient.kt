package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.NorthumberlandBadmintonConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class PlayerClient(config: NorthumberlandBadmintonConfiguration, restTemplateBuilder: RestTemplateBuilder) : AbstractClient<PlayerResource>(
    config, restTemplateBuilder, PlayerResource::class.java, config.playerRel
) {

    private val listResponseType = object : ParameterizedTypeReference<List<PlayerResource>>() {}

    override fun findAll(): List<PlayerResource> = findAll(listResponseType)
}