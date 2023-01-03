package com.andymcg.northumberlandbadmintonadmin.controller

import com.andymcg.northumberlandbadmintonadmin.client.AbstractClient
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

annotation class AllOpen

@AllOpen
open class AbstractRestResourceController<
    RESOURCE,
    FORM : ResourceForm<RESOURCE>,
    FACTORY : ResourceFormFactory<RESOURCE, FORM>,
    CLIENT : AbstractClient<RESOURCE>>(val client: CLIENT, private val fac: FACTORY, rel: String) {

    val listPageView = rel
    val listPageRedirectView = "redirect:${com.andymcg.northumberlandbadmintonadmin.Uris.root}/$rel"
    val createView = "$rel.new"
    val editView = "$rel.edit"

    open fun doFindAll(): List<RESOURCE> = client.findAll()

    open fun beforeCreateResource(form: FORM): FORM = form

    open fun beforeUpdateResource(form: FORM, resource: RESOURCE): FORM = form

    @GetMapping
    fun displayList(model: MutableMap<String, Any>): String {
        model["resources"] = doFindAll()
        return listPageView
    }

    @GetMapping("/new")
    fun displayCreate(model: MutableMap<String, Any>): String {
        model["form"] = fac.newForm()
        return createView
    }

    @PostMapping
    fun handleCreate(form: FORM, errors: Errors, model: MutableMap<String, Any>): String {
        if (errors.hasErrors()) {
            model["form"] = form
            return createView
        }
        val resource = beforeCreateResource(form).toNewResource()
        client.post(resource)
        return listPageRedirectView
    }

    @GetMapping("/{id}")
    fun displayEdit(@PathVariable("id") id: Long, model: MutableMap<String, Any>): String {
        client.findById(id)?.let {
            model["resource"] = it
            model["form"] = fac.editForm(it)
            return editView
        } ?: throw IllegalArgumentException()
    }

    @PostMapping("/{id}/update")
    fun handleEdit(
        @PathVariable("id") id: Long,
        form: FORM,
        errors: Errors,
        model: MutableMap<String, Any>
    ): String {
        if (errors.hasErrors()) {
            model["form"] = form
            return editView
        }
        client.findById(id)?.let {
            val updated = beforeUpdateResource(form, it).updatedCopy(it)
            client.put(updated, id)
            return listPageRedirectView
        } ?: throw IllegalArgumentException()
    }

    @PostMapping("/{id}/delete")
    fun handleDelete(@PathVariable("id") id: Long): String {
        client.delete(id)
        return listPageRedirectView
    }
}

interface ResourceForm<RES> {
    fun toNewResource(): RES
    fun updatedCopy(resource: RES): RES
}

interface ResourceFormFactory<RES, FORM : ResourceForm<RES>> {
    fun editForm(resource: RES): FORM
    fun newForm(): FORM
}

