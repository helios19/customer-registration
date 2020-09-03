package com.nsw.cs.registrations.service

import com.nsw.cs.registrations.model.Registration
import org.springframework.data.domain.Pageable

interface RegistrationService {
    fun findAll(): List<Registration>
    fun findAll(pageable: Pageable): List<Registration>
}
