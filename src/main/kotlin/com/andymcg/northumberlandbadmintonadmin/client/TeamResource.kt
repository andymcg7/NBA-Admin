package com.andymcg.northumberlandbadmintonadmin.client

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.stereotype.Component

enum class Players(gender: Gender?) {
    MALE(Gender.MALE),
    FEMALE(Gender.FEMALE),
    ALL(null)
}

enum class MatchType(name: String, playersType: Players?) {
    MENS_DOUBLES("Mens Doubles", Players.MALE),
    LADIES_DOUBLES("Ladies Doubles", Players.FEMALE),
    MIXED_DOUBLES("Mixed Doubles", Players.ALL)
}

//@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonSerialize(using = TeamSerializer::class)
@JsonDeserialize(using = TeamDeserializer::class)
data class TeamResource(
    val id: Long?,
    val teamName: String,
    val type: MatchType,
//    @JsonProperty(value = "club_id")
    val club: ClubResource?,
    val contact: String,
    val division: String,
    val abbreviation: String
)

@Component
class TeamSerializer : JsonSerializer<TeamResource>() {

    override fun serialize(team: TeamResource?, jsonGenerator: JsonGenerator?, p2: SerializerProvider) {
        jsonGenerator?.let { generator ->
            team?.let {
                generator.writeStartObject()
                generator.writeNumberField("id", it.id!!)
                generator.writeStringField("team_name", it.teamName)
                generator.writeStringField("type", it.type.name)
                generator.writeNumberField("club_id", it.club!!.id!!)
                generator.writeStringField("contact", it.contact)
                generator.writeStringField("division", it.division)
                generator.writeStringField("abbreviation", it.abbreviation)
                generator.writeEndObject()
            } ?: generator.writeNull()
        }
    }
}

@Component
class TeamDeserializer(private val clubClient: ClubClient) : JsonDeserializer<TeamResource>() {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): TeamResource {
        val node = p.readValueAsTree<JsonNode>()
        val clubId = node.get("club_id").asLong()
        return TeamResource(id = node.get("id").asLong(),
                            teamName = node.get("team_name").asText(),
                            type = MatchType.valueOf(node.get("type").asText()),
                            club = clubClient.findById(clubId) ?: throw IllegalStateException("can't deserialize club id $clubId"),
                            contact = node.get("contact").asText(),
                            division = node.get("division").asText(),
                            abbreviation = node.get("abbreviation").asText())
    }

}