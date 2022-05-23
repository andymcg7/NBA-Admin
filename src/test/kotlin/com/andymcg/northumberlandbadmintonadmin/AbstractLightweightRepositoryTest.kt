package com.andymcg.northumberlandbadmintonadmin

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Rollback
@Transactional
@SpringBootTest
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.DOCKER)
@AutoConfigureTestEntityManager
abstract class AbstractLightweightRepositoryTest {

    @Autowired
    lateinit var em: EntityManager

    fun clear() = em.clear()

    fun <T> deleteAll(clazz: Class<T>) {
        val found = findAll(clazz)
        for (entity in found) {
            em.remove(entity)
        }
    }

    fun <T> getOne(clazz: Class<T>, id: Long): T = findOne(clazz, id)!!

    fun <T> findAll(clazz: Class<T>): List<T> {
        val cb = em.criteriaBuilder
        val cq = cb.createQuery(clazz)
        val root = cq.from(clazz)
        val all = cq.select(root)
        val q = em.createQuery(all)
        return q.resultList
    }

    fun <T> findOne(clazz: Class<T>, id: Long): T? = em.find(clazz, id)

    fun flush() = em.flush()

    fun flushAndClear() {
        flush()
        clear()
    }

    fun <T> persist(entities: List<T>) = entities.forEach { em.persist(it) }

    fun <T> persistAndFlush(entities: List<T>) {
        persist(entities)
        flush()
    }

    fun <T> persistFlushAndClear(entities: List<T>) {
        persistAndFlush(entities)
        clear()
    }
}