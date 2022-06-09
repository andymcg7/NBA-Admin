package com.andymcg.northumberlandbadmintonadmin.stub

import org.springframework.beans.BeanUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import kotlin.Comparator

object StubUris {
    const val v1 = "/api/v1"
}

open class AbstractJpaRestServiceStub<RES, R : AbstractRestServiceStubService<RES>>(
    val resourceType: Class<RES>,
    val repository: R,
    val rel: String,
    val idExtractor: (entity: RES) -> Long?
) {

    @GetMapping
    fun list(): HttpEntity<List<RES>> = doFindAll()

    @GetMapping(params = ["sort", "!page"])
    fun list(sort: Sort): HttpEntity<List<RES>> = doFindAll(sort)

    @GetMapping(params = ["page"])
    fun list(page: Pageable): HttpEntity<List<RES>> = doFindAll(page)

    @GetMapping("/{id}")
    fun read(@PathVariable("id") id: Long): HttpEntity<RES> = doFindById(id)

    @PostMapping
    fun insert(@RequestBody resource: RES, uri: UriComponentsBuilder): HttpEntity<RES> =
        doSave(resource, uri)

    @PutMapping
    fun update(@RequestBody resource: RES, uri: UriComponentsBuilder): HttpEntity<RES> =
        doSave(resource, uri)

    @DeleteMapping
    fun delete(@PathVariable("id") id: Long): HttpEntity<Void> = doDeleteById(id)

    private fun doFindAll(): HttpEntity<List<RES>> = ResponseEntity.ok(repository.findAll())

    private fun doFindAll(sort: Sort): HttpEntity<List<RES>> = ResponseEntity.ok(repository.findAll(sort))

    private fun doFindAll(page: Pageable): HttpEntity<List<RES>> =
        ResponseEntity.ok(repository.findAll(page).content)

    private fun doFindById(id: Long): HttpEntity<RES> =
        repository
            .findById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    private fun doSave(resource: RES, uri: UriComponentsBuilder): HttpEntity<RES> =
        try {
            val entity =
                if (idExtractor(resource) != null) repository.update(resource)
                else repository.save(resource)
            ResponseEntity.created(
                uri.replacePath("${StubUris.v1}/$rel/{id}")
                    .buildAndExpand(idExtractor(entity))
                    .toUri()
            ).body(entity)
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity.badRequest().build()
        }

    private fun doDeleteById(id: Long): ResponseEntity<Void> {
        repository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}

open class AbstractRestServiceStubService<T>(
    val clazz: Class<T>,
    val idExtractor: (entity: T) -> Long?,
    val idSetter: (entity: T, id: Long) -> T
) {

    private val repository: MutableList<T> = mutableListOf()

    fun findById(id: Long): T? = repository.firstOrNull { idExtractor(it) == id }

    fun findAll(page: Pageable): Page<T> =
        org.springframework.data.domain.PageImpl(repository, page, repository.size.toLong())

    fun findAll(sort: Sort): List<T> = repository.sortedWith(convert(sort))
    fun findAll(): List<T> = repository

    fun addItems(items: List<T>): List<T> = items.map { save(it) }

    open fun update(item: T): T {
        val id =
            idExtractor(item)?.let { itemId ->
                deleteById(itemId)
                itemId
            } ?: getNextId()
        val itemWithId = idSetter(item, id)
        repository.add(itemWithId)
        return itemWithId
    }

    open fun save(item: T): T {
        val id = getNextId()
        val itemWithId = idSetter(item, id)
        repository.add(itemWithId)
        return itemWithId
    }

    fun deleteById(id: Long) {
        val item = repository.find { idExtractor(it) == id }
        item?.let{ repository.remove(it) }
    }

    fun reset() {
        repository.clear()
    }

    fun getNextId(): Long = (repository.map { idExtractor(it) ?: 0 }.maxByOrNull { it } ?: 0L) + 1

    private fun convert(sort: Sort): Comparator<T> {
        val orderIterator: Iterator<Sort.Order> = sort.iterator()
        val order = orderIterator.next()
        var comparator = convert(order)
        while(orderIterator.hasNext()) {
            comparator = comparator.thenComparing(convert(orderIterator.next()))
        }
        return comparator
    }

    @Suppress("UNCHECKED_CAST")
    private fun convert(order: Sort.Order): Comparator<T> {
        val comparator =
            Comparator.comparing<T, Comparable<Any>> {
                BeanUtils.getPropertyDescriptor(clazz, order.property)!!.readMethod.invoke(it) as
                        Comparable<Any>
            }
        return if (order.isDescending) comparator.reversed() else comparator
    }
}