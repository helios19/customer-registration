package com.nsw.cs.registrations.service

import com.nsw.cs.registrations.model.Registration
import com.nsw.cs.registrations.repository.RegistrationRepository
import com.nsw.cs.registrations.utils.Constants.Companion.REGISTRATIONS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * Service class providing read and save operations along with some caching logic for the {@link Registration} resource.
 *
 * @see RegistrationRepository
 */
@Service
@CacheConfig(cacheNames = [REGISTRATIONS])
internal class RegistrationServiceImpl : RegistrationService {

    @Autowired
    lateinit var repository: RegistrationRepository

    /**
     * {@inheritDoc}
     */
    @Cacheable
    override fun findAll(): List<Registration> {
        return repository.findAll()
    }

    /**
     * {@inheritDoc}
     */
    override fun findAll(pageable: Pageable): List<Registration> {
        val registrations: Page<Registration> = repository.findAll(pageable)
        return registrations.content
    }

    /**
     *
     */
    @CacheEvict(allEntries = true)
    fun save(registration: Registration) {
        repository.save(registration)
    }

    @CacheEvict(allEntries = true)
    fun saveAll(registrations: List<Registration>) {
        repository.saveAll(registrations)
    }

}