package com.andymcg.northumberlandbadmintonadmin

import nz.net.ultraq.thymeleaf.LayoutDialect
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@ConstructorBinding
@ConfigurationProperties("nba", ignoreInvalidFields = true)
data class NorthumberlandBadmintonConfiguration(
    val serviceUrl: String
)

@Configuration
class TemplateConfiguration {

    @Bean
    fun layoutDialect(): LayoutDialect = LayoutDialect()
}

//@Configuration
//class JacksonConfig {
//
//    @Bean
//    @Primary
//    fun objectMapper(): ObjectMapper =
//        ObjectMapper()
//            .registerModule(
//                KotlinModule.Builder()
//                    .withReflectionCacheSize(512)
//                    .configure(KotlinFeature.NullToEmptyCollection, false)
//                    .configure(KotlinFeature.NullToEmptyMap, false)
//                    .configure(KotlinFeature.NullIsSameAsDefault, false)
//                    .configure(KotlinFeature.StrictNullChecks, false)
//                    .build()
//            )
//
//    @Bean
//    fun objectMapperCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
//        Jackson2ObjectMapperBuilderCustomizer { builder ->
//            builder.deserializerByType(BigDecimal::class.java, NumberDeserializers.BigDecimalDeserializer)
//        }
//}