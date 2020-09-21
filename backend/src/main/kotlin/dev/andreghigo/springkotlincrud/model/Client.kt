package dev.andreghigo.springkotlincrud.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
data class Client(
    @Id
    val id: Long? = null,
    val name: String? = null,
    val creationDate: Date? = null
)