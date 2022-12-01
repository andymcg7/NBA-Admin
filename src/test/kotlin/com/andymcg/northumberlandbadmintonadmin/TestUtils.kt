package com.andymcg.northumberlandbadmintonadmin

import com.andymcg.northumberlandbadmintonadmin.client.*
import com.thedeanda.lorem.Lorem
import com.thedeanda.lorem.LoremIpsum
import org.apache.commons.lang3.RandomStringUtils
import java.math.BigDecimal
import java.security.SecureRandom
import kotlin.random.Random

object TestUtils {
    val lorem: Lorem = LoremIpsum.getInstance()
    private val random = SecureRandom()

    fun randomAlphabetic(length: Int): String = RandomStringUtils.randomAlphabetic(length)

    fun randomLong(): Long = Random.nextLong()

    fun randomLong(from: Long, to: Long) =
        if (from == to) to else Random.nextLong(from, to)

    fun randomDouble(from: Double, to: Double): Double = Random.nextDouble(from, to)

    fun randomBigDecimal(from: Double, to: Double): BigDecimal =
        if (from == to) from.toBigDecimal() else randomDouble(from, to).toBigDecimal().scaled()

    fun randomEmail(): String = lorem.email

    fun randomWords(count: Int) = lorem.getWords(count)

    fun generateRandomClubResource(): ClubResource =
        ClubResource(
            name = randomAlphabetic(20),
            venue = lorem.getWords(10),
            id = randomLong(1, 9999))

    fun generateRandomTeamResource(club: ClubResource = generateRandomClubResource()): TeamResource =
        TeamResource(
            teamName = randomAlphabetic(20),
            division = lorem.getWords(1),
            club = club,
            contact = randomEmail(),
            abbreviation = randomAlphabetic(6),
            type = randomEnum(MatchType::class.java),
            id = randomLong(1, 9999))

    fun generateRandomPlayerResource(mainClub: ClubResource? = null, secondClub: ClubResource? = null):PlayerResource =
        PlayerResource(
            name = "${lorem.lastName},${lorem.firstName}",
            primaryClub = mainClub,
            secondaryClub = secondClub,
            gender = randomEnum(Gender::class.java),
            singlesGrade = randomEnum(Grades::class.java),
            doublesGrade = randomEnum(Grades::class.java),
            mixedGrade = randomEnum(Grades::class.java),
            singlesGradeHistory = lorem.getWords(50),
            doublesGradeHistory = lorem.getWords(50),
            mixedGradeHistory = lorem.getWords(50),
            singlesAverage = randomBigDecimal(0.00, 1024.00),
            singlesDemotionAverage = randomBigDecimal(0.00, 1024.00),
            doublesAverage = randomBigDecimal(0.00, 1024.00),
            doublesDemotionAverage = randomBigDecimal(0.00, 1024.00),
            mixedAverage = randomBigDecimal(0.00, 1024.00),
            mixedDemotionAverage = randomBigDecimal(0.00, 1024.00),
            id = randomLong(1, 9999)
        )

    fun <T : Enum<*>?> randomEnum(clazz: Class<T>): T {
        val x: Int = random.nextInt(clazz.enumConstants.size)
        return clazz.enumConstants[x]
    }
}