package com.andymcg.northumberlandbadmintonadmin

import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager


@Rollback
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@AutoConfigureEmbeddedDatabase(provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.DOCKER)
abstract class AbstractApiTest<T : AbstractJpaEntity>
@Autowired
constructor(val ctx: GenericApplicationContext, val mvc: MockMvc, override val em: EntityManager) :
        EntityManagerHelper<T>