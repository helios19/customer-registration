package com.nsw.cs.registrations

import com.nsw.cs.registrations.repository.RegistrationRepository
import com.nsw.cs.registrations.utils.Constants.Companion.REGO_SAMPLE1
import com.nsw.cs.registrations.utils.Constants.Companion.REGO_SAMPLE2
import com.nsw.cs.registrations.utils.Constants.Companion.REGO_SAMPLE3
import com.nsw.cs.registrations.utils.Constants.Companion.REGO_SAMPLE4
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.nsw.cs.registrations.repository"])
@SpringBootApplication
class Application {

    @Bean
    fun init(repository: RegistrationRepository) = CommandLineRunner {
        repository.saveAll(listOf(REGO_SAMPLE1, REGO_SAMPLE2, REGO_SAMPLE3, REGO_SAMPLE4))
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
