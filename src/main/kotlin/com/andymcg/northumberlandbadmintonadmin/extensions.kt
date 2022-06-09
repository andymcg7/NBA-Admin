package com.andymcg.northumberlandbadmintonadmin

import java.math.BigDecimal
import java.math.RoundingMode

fun Int.toScaledBigDecimal(): BigDecimal = toBigDecimal().scaled()

fun BigDecimal.scaled(): BigDecimal = setScale(2, RoundingMode.HALF_EVEN)