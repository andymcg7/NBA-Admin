package com.andymcg.northumberlandbadmintonadmin.club

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaRepositoryTest
import com.andymcg.northumberlandbadmintonadmin.AbstractLightweightRepositoryTest
import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomClub
import com.andymcg.northumberlandbadmintonadmin.team.Team
import com.andymcg.northumberlandbadmintonadmin.team.TeamRepository
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import javax.persistence.EntityManager

@Disabled
class ClubRepositoryTest
@Autowired
constructor(repository: ClubRepository, em: EntityManager) :
    AbstractJpaRepositoryTest<Club, ClubRepository>(repository, em) {

    override fun createRandomValidEntity(): Club =
        generateRandomClub()

    override fun mutateEntity(entity: Club) {
        entity.name = TestUtils.randomAlphabetic(20)
    }

    override fun entityClass(): Class<Club> = Club::class.java
}