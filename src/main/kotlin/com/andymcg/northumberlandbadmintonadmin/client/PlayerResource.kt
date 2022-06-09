package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.scaled
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.stereotype.Component
import java.math.BigDecimal
import javax.annotation.PostConstruct

enum class Gender() {
    MALE,
    FEMALE
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
                    generator.writeNumberField("primary_club_id", it.primaryClub.id!!)
                }
                if (it.secondaryClub != null) {
                    generator.writeNumberField("secondary_club_id", it.secondaryClub.id!!)
                }
                generator.writeStringField("singles_grade", it.singlesGrade.name)
                generator.writeNumberField("singles_matches_played", it.singlesMatchesPlayed)
                generator.writeNumberField("singles_counting_matches", it.singlesCountingMatches)
                generator.writeNumberField("singles_average", it.singlesAverage)
                generator.writeNumberField("singles_total_points", it.singlesTotalPoints)
                generator.writeNumberField("singles_best_points", it.singlesBestPoints)
                generator.writeNumberField("singles_demotion_average", it.singlesDemotionAverage)
                generator.writeStringField("singles_grade_history", it.singlesGradeHistory)

                generator.writeStringField("doubles_grade", it.doublesGrade.name)
                generator.writeNumberField("doubles_matches_played", it.doublesMatchesPlayed)
                generator.writeNumberField("doubles_counting_matches", it.doublesCountingMatches)
                generator.writeNumberField("doubles_average", it.doublesAverage)
                generator.writeNumberField("doubles_total_points", it.doublesTotalPoints)
                generator.writeNumberField("doubles_best_points", it.doublesBestPoints)
                generator.writeNumberField("doubles_demotion_average", it.doublesDemotionAverage)
                generator.writeStringField("doubles_grade_history", it.doublesGradeHistory)

                generator.writeStringField("mixed_grade", it.mixedGrade.name)
                generator.writeNumberField("mixed_matches_played", it.mixedMatchesPlayed)
                generator.writeNumberField("mixed_counting_matches", it.mixedCountingMatches)
                generator.writeNumberField("mixed_average", it.mixedAverage)
                generator.writeNumberField("mixed_total_points", it.mixedTotalPoints)
                generator.writeNumberField("mixed_best_points", it.mixedBestPoints)
                generator.writeNumberField("mixed_demotion_average", it.mixedDemotionAverage)
                generator.writeStringField("mixed_grade_history", it.mixedGradeHistory)

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
        val primaryClub = if (node.has("primary_club_id")) {
            getClub(node.get("primary_club_id").asLong())
        }
        else null
        val secondaryClub = if (node.has("secondary_club_id")) {
            getClub(node.get("secondary_club_id").asLong())
        } else null

        return PlayerResource(
            id = node.get("id").asLong(),
            name = node.get("name").asText(),
            gender = Gender.valueOf(node.get("gender").asText()),
            primaryClub = primaryClub,
            secondaryClub = secondaryClub,
            singlesGrade = Grades.valueOf(node.get("singles_grade").asText()),
            singlesMatchesPlayed = node.get("singles_matches_played").asInt(),
            singlesCountingMatches = node.get("singles_counting_matches").asInt(),
            singlesAverage = node.get("singles_average").asDouble().toBigDecimal().scaled(),
            singlesTotalPoints = node.get("singles_total_points").asInt(),
            singlesBestPoints = node.get("singles_best_points").asInt(),
            singlesDemotionAverage = node.get("singles_demotion_average").asDouble().toBigDecimal().scaled(),
            singlesGradeHistory = node.get("singles_grade_history").asText(),
            doublesGrade = Grades.valueOf(node.get("doubles_grade").asText()),
            doublesMatchesPlayed = node.get("doubles_matches_played").asInt(),
            doublesCountingMatches = node.get("doubles_counting_matches").asInt(),
            doublesAverage = node.get("doubles_average").asDouble().toBigDecimal().scaled(),
            doublesTotalPoints = node.get("doubles_total_points").asInt(),
            doublesBestPoints = node.get("doubles_best_points").asInt(),
            doublesDemotionAverage = node.get("doubles_demotion_average").asDouble().toBigDecimal().scaled(),
            doublesGradeHistory = node.get("doubles_grade_history").asText(),
            mixedGrade = Grades.valueOf(node.get("mixed_grade").asText()),
            mixedMatchesPlayed = node.get("mixed_matches_played").asInt(),
            mixedCountingMatches = node.get("mixed_counting_matches").asInt(),
            mixedAverage = node.get("mixed_average").asDouble().toBigDecimal().scaled(),
            mixedTotalPoints = node.get("mixed_total_points").asInt(),
            mixedBestPoints = node.get("mixed_best_points").asInt(),
            mixedDemotionAverage = node.get("mixed_demotion_average").asDouble().toBigDecimal().scaled(),
            mixedGradeHistory = node.get("mixed_grade_history").asText())
    }

}