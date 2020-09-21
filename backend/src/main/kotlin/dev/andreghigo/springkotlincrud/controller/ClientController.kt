package dev.andreghigo.springkotlincrud.controller

import dev.andreghigo.springkotlincrud.model.Client
import dev.andreghigo.springkotlincrud.model.Transaction
import dev.andreghigo.springkotlincrud.repository.ClientRepository
import dev.andreghigo.springkotlincrud.repository.TransactionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.SortDefault
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@CrossOrigin("*")
@EnableSpringDataWebSupport
class ClientController(private val clientRepository: ClientRepository, private val transactionRepository: TransactionRepository) {
    @GetMapping("/clients")
    fun getClients(@PageableDefault(size = 10, page = 0) pageable: Pageable) : ResponseEntity<Page<Client>> {
        return ResponseEntity.ok().body(clientRepository.findAll(pageable))
    }

    @GetMapping("/clients/{clientId}/transactions")
    fun getClientTransactions(@PathVariable("clientId") clientId : Long, @PageableDefault(size = 10, page = 0) pageable: Pageable) : ResponseEntity<Page<Transaction>> {
        return ResponseEntity.ok().body(transactionRepository.findByClient(clientId, pageable))
    }

    @GetMapping("/create-data")
    fun createData() : ResponseEntity<Any> {
        for(i in 1..100) {
            val clientId : Long = i.toLong()
            val name = "Client $clientId"
            val client = Client(clientId, name, Date())
            clientRepository.save(client)

            for(j in 1..100) {
                val amount = i * 100.0 + j
                val transactionId = UUID.randomUUID().toString()
                val description = "Transaction $transactionId of Client $clientId"
                val transaction = Transaction(transactionId, clientId, description, Date(), amount)
                transactionRepository.save(transaction)
            }
        }

        return ResponseEntity.ok().build()
    }
}