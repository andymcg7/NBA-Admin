package com.andymcg.northumberlandbadmintonadmin

import org.apache.commons.lang3.builder.CompareToBuilder
import org.springframework.data.util.ProxyUtils
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import javax.persistence.*

@MappedSuperclass
abstract class AbstractJpaEntity : Comparable<AbstractJpaEntity> {

    companion object {
        private const val serialVersionUID = 1L
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    override fun hashCode(): Int = HashCodeBuilder().append(id).toHashCode()

    override fun toString(): String = "${javaClass.simpleName}($id)"

    override fun compareTo(other: AbstractJpaEntity): Int =
        CompareToBuilder().append(id, other.id).toComparison()

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        other as AbstractJpaEntity
        return EqualsBuilder().append(id, other.id).isEquals
    }
}