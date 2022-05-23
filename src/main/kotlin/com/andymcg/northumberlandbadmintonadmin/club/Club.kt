package com.andymcg.northumberlandbadmintonadmin.club

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaEntity
import com.andymcg.northumberlandbadmintonadmin.DedupingObjectIdResolver
import com.andymcg.northumberlandbadmintonadmin.player.Player
import com.andymcg.northumberlandbadmintonadmin.team.Team
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.*
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.TemporalType

import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotBlank
import kotlin.collections.HashSet


@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator::class,
    property = "id",
    scope = Club::class,
    resolver = DedupingObjectIdResolver::class)
@Entity
class Club(
    @NotBlank
    var name: String,
    var venue: String?,

    @OneToMany(mappedBy = "primaryClub")
    val primaryPlayers: MutableSet<Player> = HashSet(),
    @OneToMany(mappedBy = "secondaryClub")
    val secondaryPlayers: MutableSet<Player> = HashSet(),

    @OneToMany(mappedBy = "club")
    val teams: MutableSet<Team> = HashSet()
) : AbstractJpaEntity() {}