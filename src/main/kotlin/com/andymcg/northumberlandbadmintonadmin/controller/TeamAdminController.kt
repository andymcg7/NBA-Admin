package com.andymcg.northumberlandbadmintonadmin.controller

import com.andymcg.northumberlandbadmintonadmin.Uris
import com.andymcg.northumberlandbadmintonadmin.client.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Controller
@RequestMapping("${Uris.root}/teams")
class TeamAdminController(client: TeamClient, override val clubClient: ClubClient) :
    AbstractRestResourceController<TeamResource, TeamResourceForm, TeamResourceFormFactory, TeamClient>(client, TeamResourceFormFactory(), "teams"),
    AllClubs, MatchTypes {}

class TeamResourceForm() : ResourceForm<TeamResource> {

    constructor(resource: TeamResource) : this() {
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

    var club: ClubResource? = null

    @Email
    var contact: String? = null

    @Size(min = 1, max = 6)
    var abbreviation: String? = null

    override fun toNewResource(): TeamResource =
        TeamResource(
            id = id,
            teamName = teamName!!,
            type = type,
            division = division!!,
            club = club!!,
            contact = contact!!,
            abbreviation = abbreviation!!)

    override fun updatedCopy(resource: TeamResource): TeamResource =
        resource.copy(
            id = id,
            teamName = teamName!!,
            type = type,
            division = division!!,
            club = club!!,
            contact = contact!!,
            abbreviation = abbreviation!!
        )
}

class TeamResourceFormFactory : ResourceFormFactory<TeamResource, TeamResourceForm> {
    override fun editForm(resource: TeamResource): TeamResourceForm =
        TeamResourceForm(resource)

    override fun newForm(): TeamResourceForm = TeamResourceForm()
}

interface AllClubs {

    val clubClient: ClubClient

    @ModelAttribute("allClubs")
    fun findAllClubs(): List<ClubResource> =
        clubClient.findAll()

    @ModelAttribute("allClubsWithNoClubOption")
    fun findAllClubsWithNoClubOption(): List<ClubResource> {
        return listOf(ClubResource(id = null, name = "No club")) + findAllClubs()
    }
}


interface MatchTypes {

    @ModelAttribute("matchTypes")
    fun matchTypes(): List<MatchType> = MatchType.values().toList()

}