package com.andymcg.northumberlandbadmintonadmin

import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional
import kotlin.random.Random

internal class KotlinMySQLContainer(val image: String) : MySQLContainer<KotlinMySQLContainer>(image)

@Rollback
@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
@ActiveProfiles("api-test")
@Testcontainers
//@Sql("classpath:stuff.sql")
abstract class AbstractJpaRepositoryTest<T : AbstractJpaEntity, R : AbstractJpaRepository<T>>(
    val repository: R,
    override val em: EntityManager
) : EntityManagerHelper<T> {

    companion object {
        @Container
        private val mysqlContainer = KotlinMySQLContainer(image = "mysql:8.0.22")
            .withDatabaseName("nba")
            .withUrlParam("sessionVariables", "sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.jpa.hibernate.ddl-auto") { "create-drop" }
        }
    }

    abstract fun createRandomValidEntity(): T
    abstract fun mutateEntity(entity: T)

    @Test
    fun `should return count of entities`() {
        // given
        val count = Random.nextLong(1, 10)
        val entities = (1..count).map { createRandomValidEntity() }
        persistAndFlush(entities)

        // when
        val actualCount = repository.count()

        // then
        then(actualCount).isEqualTo(count)
    }

    @Test
    fun `should delete entity with given Id when delete by Id`() {
        // given
        val entity = createRandomValidEntity()
        persistAndFlush(entity)

        // when
        repository.deleteById(entity.id)

        // then
        val found = find(entity.id)
        then(found).isNull()
    }

    @Test
    fun `should delete given entity when delete`() {
        // given
        val entity = createRandomValidEntity()
        persistAndFlush(entity)

        // when
        repository.delete(entity)

        // then
        val found = find(entity.id)
        then(found).isNull()
    }

    @Test
    fun `should delete all given entities when delete all`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        persistAndFlush(listOf(first, second, third))

        // when
        repository.deleteAll(listOf(first, third))

        // then
        val found = findAll()
        then(found).containsExactlyElementsOf(listOf(second))
    }

    @Test
    fun `should delete all entities when delete all`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        persistAndFlush(listOf(first, second, third))

        // when
        repository.deleteAll()

        // then
        val found = repository.findAll()
        then(found).isEmpty()
    }

    @Test
    fun `should return true given existing Id when exists by Id`() {
        // given
        val entity = createRandomValidEntity()
        persistAndFlush(entity)

        // when
        val exists = repository.existsById(entity.id)

        // then
        then(exists).isTrue
    }

    @Test
    fun `should return false given non-existent Id when exists by Id`() {
        // when...then
        then(repository.existsById(Random.nextLong())).isFalse
    }

    @Test
    fun `should return all entities when find all`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        persistAndFlush(listOf(first, second, third))

        // when
        val found = repository.findAll()

        // then
        then(found).containsExactlyInAnyOrderElementsOf(listOf(first, second, third))
    }

    @Test
    fun `should return all entities in given order when find all with sort`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        val fourth = createRandomValidEntity()
        val entities = listOf(third, second, fourth, first)
        persistAndFlush(entities)

        // when
        val found = repository.findAll(Sort.by("id"))

        // then
        val expectedEntities = entities.sortedBy { it.id }
        then(found).containsExactlyElementsOf(expectedEntities)
    }

    @Test
    fun `should return page of entities when find all with pageable`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        val fourth = createRandomValidEntity()
        val entities = listOf(third, second, fourth, first)
        persistAndFlush(entities)

        // when
        val found = repository.findAll(PageRequest.of(1, 2, Sort.by("id")))

        // then
        val expectedEntities = entities.sortedBy { it.id }.takeLast(2)
        then(found.content).containsExactlyElementsOf(expectedEntities)
    }

    @Test
    fun `should return entities with given Ids when find all by Ids`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        val fourth = createRandomValidEntity()
        val entities = listOf(third, second, fourth, first)
        persistAndFlush(entities)

        // when
        val found = repository.findAllById(listOf(fourth.id, second.id))

        // then
        then(found).containsExactlyInAnyOrderElementsOf(listOf(second, fourth))
    }

    @Test
    fun `should return entity with given Id when find by Id`() {
        // given
        val entity = createRandomValidEntity()
        persistAndFlush(listOf(entity))

        // when
        val found = repository.findByIdOrNull(entity.id)

        // then
        then(found).isEqualTo(entity)
    }

    @Test
    fun `should return none given non-existent Id when find by Id`() {
        // when...then
        then(repository.findById(Random.nextLong())).isEqualTo(Optional.empty<T>())
    }

    @Test
    fun `should return entity with given Id when get by Id`() {
        // given
        val entity = createRandomValidEntity()
        persistAndFlush(listOf(entity))

        // when
        val found = repository.getReferenceById(entity.id)

        // then
        then(found).isEqualTo(entity)
    }

    @Test
    fun `should throw given non-existent Id when get one`() {
        // when...then
        assertThrows<EntityNotFoundException> {
            val found = repository.getReferenceById(Random.nextLong(1, 1000))
            then(found.toString()).isNull()
        }
    }

    @Test
    fun `should save valid entity`() {
        // given
        val entity = createRandomValidEntity()

        // when
        repository.save(entity)

        // then
        flush()
        val found = find(entity.id)
        then(found).isEqualTo(entity)
    }

    @Test
    fun `should save given valid entities when save all`() {
        // given
        val first = createRandomValidEntity()
        val second = createRandomValidEntity()
        val third = createRandomValidEntity()
        val fourth = createRandomValidEntity()
        val entities = listOf(third, second, fourth, first)

        // when
        repository.saveAll(entities)

        // then
        flush()
        val found = findAll()
        then(found).containsExactlyInAnyOrderElementsOf(entities)
    }
}