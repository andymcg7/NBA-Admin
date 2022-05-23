package com.andymcg.northumberlandbadmintonadmin

import org.springframework.data.repository.findByIdOrNull
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.lang.IllegalArgumentException

annotation class AllOpen

@AllOpen
open class AbstractRestResourceController<
    T : AbstractJpaEntity,
    FORM : ResourceForm<T>,
    FAC : ResourceFormFactory<T, FORM>,
    REPO : AbstractJpaRepository<T>>(val repo: REPO, val fac: FAC, val rel: String) {

    val listPageView = rel
    val listPageRedirectView = "redirect:${Uris.root}/$rel"
    val createView = "$rel.new"
    val editView = "$rel.edit"

    open fun doFindAll(): List<T> =
        repo.findAll()

    open fun beforeCreateResource(form: FORM): FORM = form

    open fun beforeUpdateResource(form: FORM, resource: T): FORM = form

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
        repo.save(resource)
        return listPageRedirectView
    }

    @GetMapping("/{id}")
    fun displayEdit(@PathVariable("id") id: Long, model: MutableMap<String, Any>): String {
        repo.findByIdOrNull(id)?.let {
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
        repo.findByIdOrNull(id)?.let {
            val updated = beforeUpdateResource(form, it).updatedCopy(it)
            repo.save(updated)
            return listPageRedirectView
        } ?: throw IllegalArgumentException()
    }

    @PostMapping("/{id}/delete")
    fun handleDelete(@PathVariable("id") id: Long): String {
        repo.deleteById(id)
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

