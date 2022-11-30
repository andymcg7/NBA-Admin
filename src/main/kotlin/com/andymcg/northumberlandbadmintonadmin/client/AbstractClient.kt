package com.andymcg.northumberlandbadmintonadmin.client

import com.andymcg.northumberlandbadmintonadmin.NorthumberlandBadmintonConfiguration
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

abstract class AbstractClient<T>(
    private val config: NorthumberlandBadmintonConfiguration,
    private val restTemplateBuilder: RestTemplateBuilder,
    private val clazz: Class<T>,
    private val rel: String
) {

    private val rest: RestTemplate by lazy {
        restTemplateBuilder
            .rootUri("${config.serviceUrl}/api/v1")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("x-api-key", config.apiKey)
            .additionalMessageConverters(MappingJackson2HttpMessageConverter())
            .build()
    }

    abstract fun findAll(): List<T>

    fun findById(id: Long): T? = rest.getForObject("/$rel/$id", clazz)

    fun findAll(responseType: ParameterizedTypeReference<List<T>>): List<T> =
        rest.exchange("/$rel", HttpMethod.GET, null, responseType).body ?: emptyList()

    fun post(entity: T): T =
        rest.postForObject("/$rel", entity, clazz) ?: throw IllegalStateException()

    fun put(entity: T, id: Long): T? {
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        val httpEntity: HttpEntity<T> = HttpEntity<T>(entity, headers)
        return rest.exchange("/$rel/$id", HttpMethod.PUT, httpEntity, clazz).body
    }

    fun delete(id: Long) {
        rest.delete("/$rel/$id")
    }
}