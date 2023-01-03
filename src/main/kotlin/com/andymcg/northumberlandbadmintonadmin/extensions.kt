package com.andymcg.northumberlandbadmintonadmin

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.scaled(): BigDecimal = setScale(2, RoundingMode.HALF_EVEN)

inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? =
    T::class.java.enumConstants.firstOrNull { it.name == name }