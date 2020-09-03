package com.nsw.cs.registration.controller

/**
 * Created by helios on 2/09/20.
 */


import com.nsw.cs.registrations.Application
import com.nsw.cs.registrations.repository.RegistrationRepository
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext


@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
class RegistrationControllerIT {

    private lateinit var mockMvc: MockMvc

    private val contentType = MediaType.APPLICATION_JSON_VALUE

    @Autowired
    private lateinit var repository: RegistrationRepository

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext


    @Before
    @Throws(Exception::class)
    fun setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).build()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
    }

    @org.junit.Test
    @Throws(Exception::class)
    fun shouldReturnAllRegistrations() {
        mockMvc.perform(get("/registrations/"))
                .andDo(print())
                .andExpect(status().isOk).andExpect(content().contentType(contentType))
                .andExpect(jsonPath("registrations.[0].plate_number", `is`("EBF28E")))
                .andExpect(jsonPath("registrations.[0].registration.expiry_date", equalTo("2021-02-05T23:15:30.000Z")))
                .andExpect(jsonPath("registrations.[0].vehicule.type", equalTo("Wagon")))
                .andExpect(jsonPath("registrations.[0].vehicule.make", equalTo("BMW")))
                .andExpect(jsonPath("registrations.[0].vehicule.colour", equalTo("Blue")))
                .andExpect(jsonPath("registrations.[0].vehicule.vin", equalTo("12389347324")))
                .andExpect(jsonPath("registrations.[0].vehicule.tare_weight", equalTo(1700)))
                .andExpect(jsonPath("registrations.[0].vehicule.gross_mass", nullValue()))
    }
}
