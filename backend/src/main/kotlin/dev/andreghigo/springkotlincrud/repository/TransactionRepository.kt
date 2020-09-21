package dev.andreghigo.springkotlincrud.repository

import dev.andreghigo.springkotlincrud.model.Client
import dev.andreghigo.springkotlincrud.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : PagingAndSortingRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.clientId = :clientId")
    fun findByClient(@Param("clientId") clientId: Long, pageable: Pageable): Page<Transaction>?
}