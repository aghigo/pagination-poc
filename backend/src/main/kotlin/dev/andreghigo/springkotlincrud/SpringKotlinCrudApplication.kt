package dev.andreghigo.springkotlincrud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class SpringKotlinCrudApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinCrudApplication>(*args)
}
