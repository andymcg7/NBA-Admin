package com.andymcg.northumberlandbadmintonadmin.club

import com.andymcg.northumberlandbadmintonadmin.AbstractRestResourceController
import com.andymcg.northumberlandbadmintonadmin.ResourceForm
import com.andymcg.northumberlandbadmintonadmin.ResourceFormFactory
import com.andymcg.northumberlandbadmintonadmin.player.Player
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import javax.validation.constraints.NotBlank

@RequestMapping("/clubs")
@Controller
class ClubController(val repository: ClubRepository) :
    AbstractRestResourceController<Club, ClubForm, ClubResourceFormFactory, ClubRepository>(repository, ClubResourceFormFactory(), "clubs") {

}

class ClubForm() : ResourceForm<Club> {

    constructor(resource: Club) : this() {
        id = resource.id
        name = resource.name
        venue = resource.venue
        primaryPlayers = resource.primaryPlayers.toList()
        secondaryPlayers = resource.secondaryPlayers.toList()
    }

    var id: Long? = null

    @get:NotBlank
    var name: String? = null

    var venue: String? = null

    var primaryPlayers: List<Player> = emptyList()

    var secondaryPlayers: List<Player> = emptyList()

    override fun toNewResource(): Club =
        Club(
            name = name!!,
            venue = venue,
            primaryPlayers = primaryPlayers.toMutableSet(),
            secondaryPlayers = secondaryPlayers.toMutableSet())

    override fun updatedCopy(resource: Club): Club =
        toNewResource().apply {
            id = resource.id
        }

}

class ClubResourceFormFactory : ResourceFormFactory<Club, ClubForm> {
    override fun editForm(resource: Club): ClubForm =
        ClubForm(resource)

    override fun newForm(): ClubForm = ClubForm()
}

data class ClubTransferObject(
    val id: Long? = null,
    val name: String
)