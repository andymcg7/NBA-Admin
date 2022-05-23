package com.andymcg.northumberlandbadmintonadmin.team

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaEntity
import com.andymcg.northumberlandbadmintonadmin.DedupingObjectIdResolver
import com.andymcg.northumberlandbadmintonadmin.club.Club
import com.andymcg.northumberlandbadmintonadmin.player.Gender
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne


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

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator::class,
    property = "id",
    scope = Team::class,
    resolver = DedupingObjectIdResolver::class
)
@Entity
class Team (
    var teamName: String,
    @Enumerated(EnumType.STRING)
    var type: MatchType,
    var division: String,
    @ManyToOne(optional = false)
    var club: Club?,
    var contact: String,
    var abbreviation: String
): AbstractJpaEntity()