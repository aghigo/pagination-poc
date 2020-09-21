package dev.andreghigo.springkotlincrud.repository

import dev.andreghigo.springkotlincrud.model.Client
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : PagingAndSortingRepository<Client, Long>