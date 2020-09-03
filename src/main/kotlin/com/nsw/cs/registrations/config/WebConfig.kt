package com.nsw.cs.registrations.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.nsw.cs.registrations.common.security.XssFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configuration class enabling web filers such as {@link XssFilter} and json converter configuration.
 */
@EnableSpringDataWebSupport
@Configuration
class WebConfig : WebMvcConfigurer {
    /**
     * Returns an {@link FilterRegistrationBean} instance wrapping a {@link XssFilter} filter
     * to be loaded by the spring context.
     *
     * @return FilterRegistrationBean instance
     */
//    @Bean
    fun xssFilterRegistrationBean(): FilterRegistrationBean<XssFilter> {
        val registrationBean = FilterRegistrationBean<XssFilter>()
        val xssFilter = XssFilter()
        registrationBean.setFilter(xssFilter)
        registrationBean.setOrder(1)
        return registrationBean
    }

    /**
     * Returns a Jackson message converter customized with the following parameters:
     * <li> - {@link DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES} disabled
     * <li> - {@link SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} disabled
     *
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    fun mappingJackson2HttpMessageConverter(): MappingJackson2HttpMessageConverter {
        return MappingJackson2HttpMessageConverter().apply {
            val objectMapper = ObjectMapper().apply {
                registerModule(KotlinModule())
            }
            objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            objectMapper.dateFormat = StdDateFormat()
            objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE;
            this.objectMapper = objectMapper
        }
    }
}