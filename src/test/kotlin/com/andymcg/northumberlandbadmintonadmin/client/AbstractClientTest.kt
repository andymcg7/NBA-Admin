package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.AbstractApiTest
import com.andymcg.northumberlandbadmintonadmin.AbstractFunctionalSpec
import com.andymcg.northumberlandbadmintonadmin.stub.AbstractRestServiceStubService
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

abstract class AbstractClientTest<RES, CLIENT : AbstractClient<RES>>(
    val idExtractor: (RES) -> Long?
): AbstractApiTest() {

    abstract fun getClient(): CLIENT

    abstract fun getStubService(): AbstractRestServiceStubService<RES>

    abstract fun randomResource(): RES

    abstract fun callConcreteFindAll(): List<RES>

    abstract fun mutate(resource: RES): RES

    @AfterEach
    fun teardown()  {
        getStubService().reset()
    }

    @Test
    fun `the client should return a list of resources`() {
        // given
        val resources = (1..5).map { randomResource() }
        getStubService().addItems(resources)

        // when
        val found = callConcreteFindAll()

        // then
        then(found)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactlyElementsOf(resources)
    }

    @Test
    fun `the client should return a single resource given a valid resource id`() {
        // given
        val randomResource = getStubService().save(randomResource())

        // when
        val found = getClient().findById(idExtractor(randomResource)!!)

        // then
        then(found).usingRecursiveComparison().ignoringFields("id").isEqualTo(randomResource)
    }

    @Test
    fun `the client should return a saved resource when POST valid resource`() {
        // given
        val randomResource = randomResource()

        // when
        val saved = getClient().post(randomResource)

        // then
        then(saved).usingRecursiveComparison().ignoringFields("id").isEqualTo(randomResource)
    }

    @Test
    fun `the client should delete a saved resource`() {
        // given
        val randomResource = randomResource()

        // when
        getClient().delete(idExtractor(randomResource)!!)

        // then
        then(getStubService().findAll().size).isZero
    }

    @Test
    fun `the client should return an updated resource when PUT valid resource`() {
        // given
        val randomResource = getStubService().save(randomResource())
        val updatedResource = mutate(randomResource)

        // when
        val updated = getClient().put(updatedResource, idExtractor(updatedResource)!!)

        // then
        then(updated).usingRecursiveComparison().isEqualTo(updatedResource)
    }
}