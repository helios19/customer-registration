package com.nsw.cs.registrations.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.nsw.cs.registrations.utils.Constants.Companion.FORMATTER_PATTERN
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

/**
 * Plain java class holding the declaration of {@link Registration}, {@link Vehicule} and {@link Insurer} resources.
 */
@Embeddable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RegistrationExp(
        var expired: Boolean,
        @JsonFormat(pattern = FORMATTER_PATTERN)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        var expiryDate: Date
)

@Entity
@JsonIgnoreProperties("hibernateLazyInitializer", "handler", "hibernate_lazy_initializer", "new")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class Vehicule(
        var type: String,
        var make: String,
        var model: String,
        var colour: String,
        var vin: String,
        var tareWeight: Int,
        var grossMass: Int? = null,
        @Version @JsonIgnore
        var version: Long = 0) : AbstractJpaPersistable<Long>()


@Entity
@JsonIgnoreProperties("hibernateLazyInitializer", "handler", "hibernate_lazy_initializer", "new")
class Insurer(
        var name: String,
        var code: Int,
        @Version @JsonIgnore
        var version: Long = 0) : AbstractJpaPersistable<Long>()


@Entity
@JsonIgnoreProperties("new")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class Registration(
        var plateNumber: String,
        @Embedded var registration: RegistrationExp,
        @ManyToOne(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY) var vehicule: Vehicule,
        @ManyToOne(cascade = [CascadeType.PERSIST], fetch = FetchType.LAZY) var insurer: Insurer,
        @Version @JsonIgnore
        var version: Long = 0) : AbstractJpaPersistable<Long>()

class RegistrationList(val registrations: List<Registration>)
