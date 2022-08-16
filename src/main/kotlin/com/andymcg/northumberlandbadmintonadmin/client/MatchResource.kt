package com.andymcg.northumberlandbadmintonadmin.client

data class MatchResource(
    val id: Long? = null,
    val player1: PlayerResource,
    val partner1: PlayerResource? = null,
    val player2: PlayerResource,
    val partner2: PlayerResource? = null,
    val team1Score1: Long,
    val team1Score2: Long,
    val team1Score3: Long? = null,
    val team2Score1: Long,
    val team2Score2: Long,
    val team2Score3: Long? = null
)