package com.andymcg.northumberlandbadmintonadmin

import com.andymcg.northumberlandbadmintonadmin.club.Club
import com.andymcg.northumberlandbadmintonadmin.player.Gender
import com.andymcg.northumberlandbadmintonadmin.player.Grades
import com.andymcg.northumberlandbadmintonadmin.player.Player
import com.andymcg.northumberlandbadmintonadmin.team.MatchType
import com.andymcg.northumberlandbadmintonadmin.team.Team
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils
import java.security.SecureRandom

object TestUtils {
    val lorem: Lorem = LoremIpsum.getInstance()
    private val random = SecureRandom()

    fun randomAlphabetic(length: Int): String = RandomStringUtils.randomAlphabetic(length)

    fun randomEmail(): String = lorem.email

    fun generateRandomClub(): Club =
            Club(name = randomAlphabetic(20), venue = lorem.getWords(10))

    fun generateRandomTeam(club: Club = generateRandomClub()): Team =
        Team(
            teamName = randomAlphabetic(20),
            division = lorem.getWords(1),
            club = club,
            contact = randomEmail(),
            abbreviation = randomAlphabetic(6),
            type = randomEnum(MatchType::class.java))

    fun generateRandomPlayer(mainClub: Club? = null, secondClub: Club? = null) =
        Player(
            name = "${lorem.lastName},${lorem.firstName}",
            primaryClub = mainClub,
            secondaryClub = secondClub,
            gender = randomEnum(Gender::class.java),
            singlesGrade = randomEnum(Grades::class.java),
            doublesGrade = randomEnum(Grades::class.java),
            mixedGrade = randomEnum(Grades::class.java),
            singlesGradeHistory = lorem.getWords(50),
            doublesGradeHistory = lorem.getWords(50),
            mixedGradeHistory = lorem.getWords(50)
        )

    fun <T : Enum<*>?> randomEnum(clazz: Class<T>): T {
        val x: Int = random.nextInt(clazz.enumConstants.size)
        return clazz.enumConstants[x]
    }
}