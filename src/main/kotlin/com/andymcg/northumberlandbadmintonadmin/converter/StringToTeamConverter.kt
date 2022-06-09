package com.andymcg.northumberlandbadmintonadmin.converter

import com.andymcg.northumberlandbadmintonadmin.client.TeamClient
import com.andymcg.northumberlandbadmintonadmin.client.TeamResource
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToTeamConverter(val client: TeamClient) : Converter<String, TeamResource> {

    override fun convert(source: String): TeamResource? =
        try {
            client.findById(source.toLong())
        } catch (e: Exception) {
            null
        }
}