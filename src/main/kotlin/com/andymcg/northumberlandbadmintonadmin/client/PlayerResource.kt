package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.scaled
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.stereotype.Component
import java.math.BigDecimal

enum class Gender() {
    Male,
    Female
}

enum class Grades(points: Int) {
    A2(1024),
    B1(512),
    B2(256),
    C1(128),
    C2(64),
    D1(32),
    D2(16),
    E1(8),
    E2(4),
    UG(0)
}

@JsonSerialize(using = PlayerSerializer::class)
@JsonDeserialize(using = PlayerDeserializer::class)
data class PlayerResource(
    val id: Long?,
    val name: String,
    val gender: Gender,
    val primaryClub: ClubResource?,
    val secondaryClub: ClubResource?,
    val singlesGrade: Grades = Grades.UG,
    val singlesMatchesPlayed: Int = 0,
    val singlesCountingMatches: Int = 0,
    val singlesAverage: BigDecimal = BigDecimal.ZERO,
    val singlesTotalPoints: Int = 0,
    val singlesBestPoints: Int = 0,
    val singlesDemotionAverage: BigDecimal = BigDecimal.ZERO,
    val doublesGrade: Grades = Grades.UG,
    val doublesMatchesPlayed: Int = 0,
    val doublesCountingMatches: Int = 0,
    val doublesAverage: BigDecimal = BigDecimal.ZERO,
    val doublesTotalPoints: Int = 0,
    val doublesBestPoints: Int = 0,
    val doublesDemotionAverage: BigDecimal = BigDecimal.ZERO,
    val mixedGrade: Grades = Grades.UG,
    val mixedMatchesPlayed: Int = 0,
    val mixedCountingMatches: Int = 0,
    val mixedAverage: BigDecimal = BigDecimal.ZERO,
    val mixedTotalPoints: Int = 0,
    val mixedBestPoints: Int = 0,
    val mixedDemotionAverage: BigDecimal = BigDecimal.ZERO,
    val singlesGradeHistory: String = "",
    val doublesGradeHistory: String = "",
    val mixedGradeHistory: String = ""
)

@Component
class PlayerSerializer : JsonSerializer<PlayerResource>() {

    override fun serialize(team: PlayerResource?, jsonGenerator: JsonGenerator?, p2: SerializerProvider) {
        jsonGenerator?.let { generator ->
            team?.let {
                generator.writeStartObject()
                generator.writeNumberField("id", it.id!!)
                generator.writeStringField("name", it.name)
                generator.writeStringField("gender", it.gender.name)
                if (it.primaryClub != null) {
                    generator.writeNumberField("primaryClub", it.primaryClub.id!!)
                }
                if (it.secondaryClub != null) {
                    generator.writeNumberField("secondaryClub", it.secondaryClub.id!!)
                }
                generator.writeStringField("singlesGrade", it.singlesGrade.name)
                generator.writeNumberField("singlesMatchesPlayed", it.singlesMatchesPlayed)
                generator.writeNumberField("singlesCountingMatches", it.singlesCountingMatches)
                generator.writeNumberField("singlesAverage", it.singlesAverage)
                generator.writeNumberField("singlesTotalPoints", it.singlesTotalPoints)
                generator.writeNumberField("singlesBestPoints", it.singlesBestPoints)
                generator.writeNumberField("singlesDemotionAverage", it.singlesDemotionAverage)
                generator.writeStringField("singlesGradeHistory", it.singlesGradeHistory)

                generator.writeStringField("doublesGrade", it.doublesGrade.name)
                generator.writeNumberField("doublesMatchesPlayed", it.doublesMatchesPlayed)
                generator.writeNumberField("doublesCountingMatches", it.doublesCountingMatches)
                generator.writeNumberField("doublesAverage", it.doublesAverage)
                generator.writeNumberField("doublesTotalPoints", it.doublesTotalPoints)
                generator.writeNumberField("doublesBestPoints", it.doublesBestPoints)
                generator.writeNumberField("doublesDemotionAverage", it.doublesDemotionAverage)
                generator.writeStringField("doublesGradeHistory", it.doublesGradeHistory)

                generator.writeStringField("mixedGrade", it.mixedGrade.name)
                generator.writeNumberField("mixedMatchesPlayed", it.mixedMatchesPlayed)
                generator.writeNumberField("mixedCountingMatches", it.mixedCountingMatches)
                generator.writeNumberField("mixedAverage", it.mixedAverage)
                generator.writeNumberField("mixedTotalPoints", it.mixedTotalPoints)
                generator.writeNumberField("mixedBestPoints", it.mixedBestPoints)
                generator.writeNumberField("mixedDemotionAverage", it.mixedDemotionAverage)
                generator.writeStringField("mixedGradeHistory", it.mixedGradeHistory)

                generator.writeEndObject()
            } ?: generator.writeNull()
        }
    }
}

@Component
class PlayerDeserializer(private val clubClient: ClubClient) : JsonDeserializer<PlayerResource>() {

    private val clubs: List<ClubResource> by lazy {
        clubClient.findAll()
    }

    private fun getClub(id: Long): ClubResource? =
        if (id != 0L) clubs.firstOrNull { it.id == id } else null

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): PlayerResource {
        val node = p.readValueAsTree<JsonNode>()
        val primaryClub = if (node.has("primaryClub")) {
            getClub(node.get("primaryClub").asLong())
        }
        else null
        val secondaryClub = if (node.has("secondaryClub")) {
            getClub(node.get("secondaryClub").asLong())
        } else null

        return PlayerResource(
            id = node.get("id").asLong(),
            name = node.get("name").asText(),
            gender = Gender.valueOf(node.get("gender").asText()),
            primaryClub = primaryClub,
            secondaryClub = secondaryClub,
            singlesGrade = Grades.valueOf(node.get("singlesGrade").asText()),
            singlesMatchesPlayed = node.get("singlesMatchesPlayed").asInt(),
            singlesCountingMatches = node.get("singlesCountingMatches").asInt(),
            singlesAverage = node.get("singlesAverage").asDouble().toBigDecimal().scaled(),
            singlesTotalPoints = node.get("singlesTotalPoints").asInt(),
            singlesBestPoints = node.get("singlesBestPoints").asInt(),
            singlesDemotionAverage = node.get("singlesDemotionAverage").asDouble().toBigDecimal().scaled(),
            singlesGradeHistory = node.get("singlesGradeHistory").asText(),
            doublesGrade = Grades.valueOf(node.get("doublesGrade").asText()),
            doublesMatchesPlayed = node.get("doublesMatchesPlayed").asInt(),
            doublesCountingMatches = node.get("doublesCountingMatches").asInt(),
            doublesAverage = node.get("doublesAverage").asDouble().toBigDecimal().scaled(),
            doublesTotalPoints = node.get("doublesTotalPoints").asInt(),
            doublesBestPoints = node.get("doublesBestPoints").asInt(),
            doublesDemotionAverage = node.get("doublesDemotionAverage").asDouble().toBigDecimal().scaled(),
            doublesGradeHistory = node.get("doublesGradeHistory").asText(),
            mixedGrade = Grades.valueOf(node.get("mixedGrade").asText()),
            mixedMatchesPlayed = node.get("mixedMatchesPlayed").asInt(),
            mixedCountingMatches = node.get("mixedCountingMatches").asInt(),
            mixedAverage = node.get("mixedAverage").asDouble().toBigDecimal().scaled(),
            mixedTotalPoints = node.get("mixedTotalPoints").asInt(),
            mixedBestPoints = node.get("mixedBestPoints").asInt(),
            mixedDemotionAverage = node.get("mixedDemotionAverage").asDouble().toBigDecimal().scaled(),
            mixedGradeHistory = node.get("mixedGradeHistory").asText())
    }

}