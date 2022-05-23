package com.andymcg.northumberlandbadmintonadmin.team

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaRepositoryTest
import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.TestUtils.generateRandomTeam
import com.andymcg.northumberlandbadmintonadmin.club.Club
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

@Disabled
class TeamRepositoryTest
@Autowired
constructor(repository: TeamRepository, em: EntityManager) :
    AbstractJpaRepositoryTest<Team, TeamRepository>(repository, em) {

    override fun createRandomValidEntity(): Team =
        generateRandomTeam(club = randomPersistentClub())

    override fun mutateEntity(entity: Team) {
        entity.teamName = TestUtils.randomAlphabetic(20)
    }

    override fun entityClass(): Class<Team> = Team::class.java

    private fun randomPersistentClub(): Club {
        val club = TestUtils.generateRandomClub()
        persist(club)
        return club
    }
}