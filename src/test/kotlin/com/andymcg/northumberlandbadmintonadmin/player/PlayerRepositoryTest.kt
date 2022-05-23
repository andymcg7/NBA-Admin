package com.andymcg.northumberlandbadmintonadmin.player

import com.andymcg.northumberlandbadmintonadmin.AbstractJpaRepositoryTest
import com.andymcg.northumberlandbadmintonadmin.AbstractLightweightRepositoryTest
import com.andymcg.northumberlandbadmintonadmin.TestUtils
import com.andymcg.northumberlandbadmintonadmin.club.Club
import com.andymcg.northumberlandbadmintonadmin.team.Team
import com.andymcg.northumberlandbadmintonadmin.team.TeamRepository
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager

@Disabled
class PlayerRepositoryTest
@Autowired
constructor(repository: PlayerRepository, em: EntityManager) :
    AbstractJpaRepositoryTest<Player, PlayerRepository>(repository, em) {

    override fun createRandomValidEntity(): Player =
        TestUtils.generateRandomPlayer(mainClub = randomPersistentClub(), secondClub = randomPersistentClub())

    override fun mutateEntity(entity: Player) {
        entity.name = TestUtils.randomAlphabetic(20)
    }

    override fun entityClass(): Class<Player> = Player::class.java

    private fun randomPersistentClub(): Club {
        val club = TestUtils.generateRandomClub()
        persist(club)
        return club
    }
}
