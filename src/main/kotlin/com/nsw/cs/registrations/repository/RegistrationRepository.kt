package com.nsw.cs.registrations.repository

import com.nsw.cs.registrations.model.Registration
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Repository class holding the method declarations to manipulate {@link Registration} resource in database.
 * This class inherits from {@link JpaRepository}
 *
 * @see JpaRepository
 */
@Repository
interface RegistrationRepository : JpaRepository<Registration, Long> {
    override fun findAll(): List<Registration>
    override fun findAll(pageable: Pageable): Page<Registration>
}