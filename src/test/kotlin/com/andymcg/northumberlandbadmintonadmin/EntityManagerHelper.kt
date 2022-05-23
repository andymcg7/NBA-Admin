package com.andymcg.northumberlandbadmintonadmin

import javax.persistence.EntityManager

interface EntityManagerHelper<T : AbstractJpaEntity> {

    val em: EntityManager

    fun entityClass(): Class<T>

    fun persistAndFlush(entities: List<Any>) {
        persist(entities)
        flush()
    }

    fun persistAndFlush(entity: Any) {
        persist(entity)
        flush()
    }

    fun persist(entities: List<Any>) = entities.forEach{ persist(it) }

    fun persist(entity: Any) = em.persist(entity)

    fun flush() = em.flush()

    fun find(id: Long): T? = em.find(entityClass(), id)

    fun findAll(): List<T> = findAll(entityClass())

    fun <E> findAll(clazz: Class<E>): List<E> {
        val cq = em.criteriaBuilder.createQuery(clazz)
        val re = cq.from(clazz)
        val all = cq.select(re)
        return em.createQuery(all).resultList
    }

    fun deleteAll() = deleteAll(entityClass())

    fun <E> deleteAll(clazz: Class<E>) = findAll(clazz).forEach { em.remove(it) }

    fun exists(id: Long): Boolean = find(id) != null
}