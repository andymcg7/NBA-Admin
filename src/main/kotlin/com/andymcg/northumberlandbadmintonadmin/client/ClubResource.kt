package com.andymcg.northumberlandbadmintonadmin.client

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerator
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver
import org.springframework.stereotype.Component

data class ClubResource(
    val id: Long? = null,
    val name: String? = null,
    val venue: String? = null
)
