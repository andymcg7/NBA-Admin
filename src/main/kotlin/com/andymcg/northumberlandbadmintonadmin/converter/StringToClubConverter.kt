package com.andymcg.northumberlandbadmintonadmin.converter

import com.andymcg.northumberlandbadmintonadmin.client.ClubClient
import com.andymcg.northumberlandbadmintonadmin.client.ClubResource
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class StringToClubConverter(val client: ClubClient) : Converter<String, ClubResource> {

    override fun convert(source: String): ClubResource? =
        try {
            client.findById(source.toLong())
        } catch (e: Exception) {
            null
        }
}

@Component
class LongToClubConverter(val client: ClubClient) : Converter<Long, ClubResource> {

    override fun convert(source: Long): ClubResource? =
        try {
            client.findById(source)
        } catch (e: Exception) {
            null
        }
}