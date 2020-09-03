package com.nsw.cs.registration.controller


import com.jayway.restassured.http.ContentType
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given
import com.nsw.cs.registrations.controller.RegistrationController
import com.nsw.cs.registrations.service.RegistrationService
import com.nsw.cs.registrations.utils.Constants.Companion.REGO_SAMPLE1
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import javax.servlet.http.HttpServletResponse

@ActiveProfiles("test", "cacheDisabled")
@RunWith(SpringRunner::class)
class RegistrationControllerTest() {

    @Mock
    private lateinit var service: RegistrationService

    @InjectMocks
    private lateinit var controller: RegistrationController

    private lateinit var mvc: MockMvc

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
                .setMessageConverters(MappingJackson2HttpMessageConverter()).build()
        RestAssuredMockMvc.mockMvc(mvc)
    }

    @Test
    @DirtiesContext
    fun shouldReturnAllRegistrations() {

        `when`(service.findAll(anyObject())).thenReturn(listOf(REGO_SAMPLE1))

        given().`when`()
                .get("/registrations/")
                .then()
                .statusCode(HttpServletResponse.SC_OK)
                .contentType(ContentType.JSON)
                .log().all(true)
                .body("registrations[0].plate_number", equalTo("EBF28E"))
                .body("registrations[0].registration.expired", equalTo(false))
                .body("registrations[0].registration.expiry_date", equalTo("2021-02-05T23:15:30.000Z"))
                .body("registrations[0].vehicule.type", equalTo("Wagon"))
                .body("registrations[0].vehicule.make", equalTo("BMW"))
                .body("registrations[0].vehicule.colour", equalTo("Blue"))
                .body("registrations[0].vehicule.vin", equalTo("12389347324"))
                .body("registrations[0].vehicule.tare_weight", equalTo(1700))
                .body("registrations[0].vehicule.gross_mass", nullValue())
                .log().all(true)


        verify(service, times(1)).findAll(anyObject())
        verifyNoMoreInteractions(service)
    }

    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }
}