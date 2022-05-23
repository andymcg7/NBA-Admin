package com.andymcg.northumberlandbadmintonadmin

import org.springframework.data.jpa.repository.JpaRepository

interface AbstractJpaRepository<T : AbstractJpaEntity> : JpaRepository<T, Long> {
}