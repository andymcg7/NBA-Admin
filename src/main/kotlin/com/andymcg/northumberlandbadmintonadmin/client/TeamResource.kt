package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.enumValueOrNull
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.stereotype.Component

enum class Players(gender: Gender?) {
    MALE(Gender.Male),
    FEMALE(Gender.Female),
    ALL(null)
}

enum class MatchType(val dbName: String, val playersType: Players?) {
    MENS_DOUBLES("Mens Doubles", Players.MALE),
    LADIES_DOUBLES("Ladies Doubles", Players.FEMALE),
    MIXED_DOUBLES("Mixed Doubles", Players.ALL);

    companion object {
        fun fromName(name: String): MatchType =
          values().firstOrNull { it.dbName == name } ?: throw IllegalStateException("Invalid match type: $name")
    }
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
                generator.writeStringField("teamName", it.teamName)
                generator.writeStringField("type", it.type.dbName)
                generator.writeNumberField("clubID", it.club!!.id!!)
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
        val clubId = node.get("clubID").asLong()
        return TeamResource(id = node.get("id").asLong(),
                            teamName = node.get("teamName").asText(),
                            type = MatchType.fromName(node.get("type").asText()),
                            club = clubClient.findById(clubId) ?: throw IllegalStateException("can't deserialize club id $clubId"),
                            contact = node.get("contact").asText(),
                            division = node.get("division").asText(),
                            abbreviation = node.get("abbreviation").asText())
    }

}