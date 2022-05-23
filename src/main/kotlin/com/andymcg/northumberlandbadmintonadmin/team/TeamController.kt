package com.andymcg.northumberlandbadmintonadmin.team

import com.andymcg.northumberlandbadmintonadmin.AbstractRestResourceController
import com.andymcg.northumberlandbadmintonadmin.ResourceForm
import com.andymcg.northumberlandbadmintonadmin.ResourceFormFactory
import com.andymcg.northumberlandbadmintonadmin.club.Club
import com.andymcg.northumberlandbadmintonadmin.club.ClubRepository
import com.andymcg.northumberlandbadmintonadmin.club.ClubTransferObject
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Controller
@RequestMapping("/teams")
class TeamController(val repository: TeamRepository,
                     override val clubRepository: ClubRepository) :
    AbstractRestResourceController<Team, TeamForm, TeamResourceFormFactory, TeamRepository>(repository, TeamResourceFormFactory(), "teams"),
    AllClubs, MatchTypes {
}

class TeamForm() : ResourceForm<Team> {

    constructor(resource: Team) : this() {
        id = resource.id
        teamName = resource.teamName
        type = resource.type
        division = resource.division
        club = resource.club
        contact = resource.contact
        abbreviation = resource.abbreviation
    }

    var id: Long? = null

    @get:NotBlank
    var teamName: String? = null

    var type: MatchType = MatchType.MENS_DOUBLES

    var division: String? = null

    var club: Club? = null

    @Email
    var contact: String? = null

    @Size(min = 1, max = 6)
    var abbreviation: String? = null

    override fun toNewResource(): Team =
        Team(
            teamName = teamName!!,
            type = type,
            division = division!!,
            club = club,
            contact = contact!!,
            abbreviation = abbreviation!!)

    override fun updatedCopy(resource: Team): Team =
       toNewResource().apply {
            id = resource.id
        }
}

class TeamResourceFormFactory : ResourceFormFactory<Team, TeamForm> {
    override fun editForm(resource: Team): TeamForm =
        TeamForm(resource)

    override fun newForm(): TeamForm = TeamForm()
}

interface AllClubs {

    val clubRepository: ClubRepository

    @ModelAttribute("allClubs")
    fun findAllClubs(): List<ClubTransferObject> =
        clubRepository.findAll().map { ClubTransferObject(it.id, it.name) }

    @ModelAttribute("allClubsWithNoClubOption")
    fun findAllClubsWithNoClubOption(): List<ClubTransferObject> {
        return listOf(ClubTransferObject(id = null, name = "No club")) + findAllClubs()
    }
}


interface MatchTypes {

    @ModelAttribute("matchTypes")
    fun matchTypes(): List<MatchType> = MatchType.values().toList()

}