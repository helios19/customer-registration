package com.nsw.cs.registrations.controller

import com.nsw.cs.registrations.model.Registration
import com.nsw.cs.registrations.model.RegistrationList
import com.nsw.cs.registrations.service.RegistrationService
import com.nsw.cs.registrations.utils.Constants.Companion.DEFAULT_PAGE_SIZE
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Registration controller class defining the HTTP operations available for the {@link Registration} resource. This controller
 * is mainly used to return a default list of registrations from H2 in-memory database.
 *
 * @see Registration
 * @see RestController
 */
@RestController
@RequestMapping("/registrations")
class RegistrationController {

    @Autowired
    lateinit var service: RegistrationService

    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(@PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable): ResponseEntity<RegistrationList> {
        val registrations = service.findAll(pageable)

        return ResponseEntity
                .ok(RegistrationList(registrations))
    }
}


