package dev.andreghigo.springkotlincrud.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Transaction (
    @Id
    val id: String? = null,
    val clientId: Long? = null,
    val description: String? = null,
    val creationDate: Date? = null,
    val amount: Double = 0.0
)