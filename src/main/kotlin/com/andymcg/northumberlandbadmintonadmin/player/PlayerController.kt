package com.andymcg.northumberlandbadmintonadmin.player

import com.andymcg.northumberlandbadmintonadmin.AbstractRestResourceController
import com.andymcg.northumberlandbadmintonadmin.ResourceForm
import com.andymcg.northumberlandbadmintonadmin.ResourceFormFactory
import com.andymcg.northumberlandbadmintonadmin.club.Club
import com.andymcg.northumberlandbadmintonadmin.club.ClubRepository
import com.andymcg.northumberlandbadmintonadmin.team.AllClubs
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.constraints.NotBlank

@RequestMapping("/players")
@Controller
class PlayerController(val repository: PlayerRepository, override val clubRepository: ClubRepository) :
    AbstractRestResourceController<Player, PlayerForm, PlayerResourceFormFactory, PlayerRepository>(repository, PlayerResourceFormFactory(), "players"),
    AllGrades, AllClubs

class PlayerForm() : ResourceForm<Player> {

    constructor(resource: Player) : this() {
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

    var primaryClub: Club? = null

    var secondaryClub: Club? = null

    var singlesGrade: Grades? = null

    var doublesGrade: Grades? = null

    var mixedGrade: Grades? = null

    var singlesGradeHistory: String? = null
    var doublesGradeHistory: String? = null
    var mixedGradeHistory: String? = null

    override fun toNewResource(): Player =
        Player(
            name = name!!,
            gender = gender,
            primaryClub = primaryClub,
            secondaryClub = secondaryClub,
            singlesGrade = singlesGrade!!,
            doublesGrade = doublesGrade!!,
            mixedGrade = mixedGrade!!,
            singlesGradeHistory = singlesGradeHistory,
            doublesGradeHistory = doublesGradeHistory,
            mixedGradeHistory = mixedGradeHistory
        )

    override fun updatedCopy(resource: Player): Player =
        toNewResource().apply {
            id = resource.id
        }

}

class PlayerResourceFormFactory : ResourceFormFactory<Player, PlayerForm> {
    override fun editForm(resource: Player): PlayerForm = PlayerForm(resource)

    override fun newForm(): PlayerForm = PlayerForm()
}

interface AllGrades {

    @ModelAttribute("allGrades")
    fun getGrades(): List<String> = Grades.values().map { it.name }

}