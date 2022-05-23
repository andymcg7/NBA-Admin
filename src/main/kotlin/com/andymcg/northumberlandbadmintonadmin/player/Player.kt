package com.andymcg.northumberlandbadmintonadmin.player

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaEntity
import com.andymcg.northumberlandbadmintonadmin.club.Club
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import java.util.*
import javax.persistence.*

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

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator::class,
    property = "id",
    scope = Player::class
)
class Player(
    var name: String,

    @Enumerated(EnumType.STRING)
    var gender: Gender,

    @ManyToOne(optional = false)
    var primaryClub: Club?,

    @ManyToOne(optional = false)
    var secondaryClub: Club?,

    @Enumerated(EnumType.STRING)
    var singlesGrade: Grades,
    @Enumerated(EnumType.STRING)
    var doublesGrade: Grades,
    @Enumerated(EnumType.STRING)
    var mixedGrade: Grades,

    var singlesGradeHistory: String?,
    var doublesGradeHistory: String?,
    var mixedGradeHistory: String?
) : AbstractJpaEntity()