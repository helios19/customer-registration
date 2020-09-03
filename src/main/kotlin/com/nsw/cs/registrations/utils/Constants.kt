package com.nsw.cs.registrations.utils

import com.nsw.cs.registrations.model.Insurer
import com.nsw.cs.registrations.model.Registration
import com.nsw.cs.registrations.model.RegistrationExp
import com.nsw.cs.registrations.model.Vehicule
import java.time.format.DateTimeFormatter

class Constants {
    companion object {
        const val REGISTRATIONS = "registrations"
        const val FORMATTER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val FORMATTER = DateTimeFormatter.ofPattern(FORMATTER_PATTERN)
        val SIMPLE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd")
        val DATE_FORMAT_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        const val DEFAULT_PAGE_SIZE = 50
        val REGO_SAMPLE1 = Registration(
                "EBF28E",
                RegistrationExp(false, "2021-02-05 23:15:30".toSimpleDate()),
                Vehicule("Wagon", "BMW", "X4 M40i", "Blue", "12389347324", 1700),
                Insurer("Allianz", 32)
        )
        val REGO_SAMPLE2 = Registration(
                "CXD82F",
                RegistrationExp(true, "2020-03-01 23:15:30".toSimpleDate()),
                Vehicule("Hatch", "Toyota", "Corolla", "Silver", "54646546313", 1432, 1500),
                Insurer("AAMI", 17)
        )
        val REGO_SAMPLE3 = Registration(
                "WOP29P",
                RegistrationExp(false, "2020-12-08 23:15:30".toSimpleDate()),
                Vehicule("Sedan", "Mercedes", "X4 M40i", "Blue", "87676676672", 1700),
                Insurer("GIO", 13)
        )
        val REGO_SAMPLE4 = Registration(
                "QWX55Z",
                RegistrationExp(false, "2021-07-20 23:15:30".toSimpleDate()),
                Vehicule("SUV", "Jaguar", "F pace", "Green", "65465466541", 1620),
                Insurer("NRMA", 27)
        )


    }
}
