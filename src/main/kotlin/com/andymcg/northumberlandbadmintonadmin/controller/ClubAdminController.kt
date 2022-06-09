package com.andymcg.northumberlandbadmintonadmin.controller

import com.andymcg.northumberlandbadmintonadmin.Uris
import com.andymcg.northumberlandbadmintonadmin.client.ClubClient
import com.andymcg.northumberlandbadmintonadmin.client.ClubResource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("${Uris.root}/clubs")
class ClubAdminController(client: ClubClient) :
AbstractRestResourceController<
ClubResource, ClubResourceForm, ClubResourceFormFactory, ClubClient>(
    client, ClubResourceFormFactory(), "clubs"
)

class ClubResourceForm() : ResourceForm<ClubResource> {

    var id: Long? = null
    var name: String? = null
    var venue: String? = null

    constructor(resource: ClubResource) : this() {
        id = resource.id
        name = resource.name
        venue = resource.venue
    }

    override fun toNewResource(): ClubResource =
        ClubResource(id, name, venue)

    override fun updatedCopy(resource: ClubResource): ClubResource =
        resource.copy(
            id = id,
            name = name,
            venue = venue
        )
}

class ClubResourceFormFactory : ResourceFormFactory<ClubResource, ClubResourceForm> {

    override fun editForm(resource: ClubResource): ClubResourceForm =
        ClubResourceForm(resource)

    override fun newForm(): ClubResourceForm = ClubResourceForm()

}