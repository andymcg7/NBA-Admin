package com.andymcg.northumberlandbadmintonadmin.controller

import com.andymcg.northumberlandbadmintonadmin.Uris
import com.andymcg.northumberlandbadmintonadmin.client.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.constraints.NotBlank

@Controller
@RequestMapping("${Uris.root}/players")
class PlayerAdminController(client: PlayerClient, override val clubClient: ClubClient) :
    AbstractRestResourceController<
            PlayerResource, PlayerResourceForm, PlayerResourceFormFactory, PlayerClient>(
        client, PlayerResourceFormFactory(), "players"
), AllClubs, AllGrades

class PlayerResourceForm() : ResourceForm<PlayerResource> {

    constructor(resource: PlayerResource) : this() {
        id = resource.id
        name = resource.name
        gender = resource.gender
        primaryClub = resource.primaryClub
        secondaryClub = resource.secondaryClub
        singlesGrade = resource.singlesGrade
        doublesGrade = resource.doublesGrade
        mixedGrade = resource.mixedGrade
        singlesGradeHistory = resource.singlesGradeHistory
        doublesGradeHistory = resource.doublesGradeHistory
        mixedGradeHistory = resource.mixedGradeHistory
    }

    var id: Long? = null

    @get:NotBlank
    var name: String? = null

    var gender: Gender = Gender.MALE

    var primaryClub: ClubResource? = null

    var secondaryClub: ClubResource? = null

    var singlesGrade: Grades? = null

    var doublesGrade: Grades? = null

    var mixedGrade: Grades? = null

    var singlesGradeHistory: String? = null
    var doublesGradeHistory: String? = null
    var mixedGradeHistory: String? = null

    override fun toNewResource(): PlayerResource =
        PlayerResource(
            id = id,
            name = name!!,
            gender = gender,
            primaryClub = primaryClub,
            secondaryClub = secondaryClub,
            singlesGrade = singlesGrade!!,
            doublesGrade = doublesGrade!!,
            mixedGrade = mixedGrade!!,
            singlesGradeHistory = singlesGradeHistory!!,
            doublesGradeHistory = doublesGradeHistory!!,
            mixedGradeHistory = mixedGradeHistory!!
        )

    override fun updatedCopy(resource: PlayerResource): PlayerResource =
        resource.copy(
            id = id,
            name = name!!,
            gender = gender,
            primaryClub = primaryClub,
            secondaryClub = secondaryClub,
            singlesGrade = singlesGrade!!,
            doublesGrade = doublesGrade!!,
            mixedGrade = mixedGrade!!,
            singlesGradeHistory = singlesGradeHistory!!,
            doublesGradeHistory = doublesGradeHistory!!,
            mixedGradeHistory = mixedGradeHistory!!
        )

}

class PlayerResourceFormFactory : ResourceFormFactory<PlayerResource, PlayerResourceForm> {
    override fun editForm(resource: PlayerResource): PlayerResourceForm = PlayerResourceForm(resource)

    override fun newForm(): PlayerResourceForm = PlayerResourceForm()
}

interface AllGrades {

    @ModelAttribute("allGrades")
    fun getGrades(): List<String> = Grades.values().map { it.name }

}