package com.nsw.cs.registration.service


import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import com.nsw.cs.registrations.repository.RegistrationRepository
import com.nsw.cs.registrations.service.RegistrationServiceImpl
import com.nsw.cs.registrations.utils.Constants
import org.junit.Assert
import org.junit.Assert.assertEquals
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

@ActiveProfiles("test", "cacheDisabled")
@RunWith(SpringRunner::class)
class RegistrationServiceTest() {

    @Mock
    private lateinit var repository: RegistrationRepository

    @InjectMocks
    private lateinit var service: RegistrationServiceImpl

    private lateinit var mvc: MockMvc

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)
        mvc = MockMvcBuilders.standaloneSetup(service)
                .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
                .setMessageConverters(MappingJackson2HttpMessageConverter()).build()
        RestAssuredMockMvc.mockMvc(mvc)
    }

    @Test
    @DirtiesContext
    fun shouldReturnAllRegistrations() {

        // given
        `when`(repository.findAll()).thenReturn(
                listOf(Constants.REGO_SAMPLE1))

        // when
        val registrations = service.findAll()

        // then
        Assert.assertFalse(registrations.isEmpty())
        Assert.assertTrue(registrations.size == 1)
        Assert.assertNotNull(registrations.get(0))
        assertEquals(registrations.get(0).plateNumber, "EBF28E")
        assertEquals(registrations.get(0).registration.expired, false)
        assertEquals(registrations.get(0).vehicule.type, "Wagon")
        assertEquals(registrations.get(0).vehicule.make, "BMW")
        assertEquals(registrations.get(0).vehicule.colour, "Blue")
        assertEquals(registrations.get(0).vehicule.vin, "12389347324")
        assertEquals(registrations.get(0).vehicule.tareWeight, 1700)
        assertEquals(registrations.get(0).vehicule.grossMass, null)
        verify<RegistrationRepository>(repository, times(1)).findAll()
        verifyNoMoreInteractions(repository)
    }

    private fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }

}