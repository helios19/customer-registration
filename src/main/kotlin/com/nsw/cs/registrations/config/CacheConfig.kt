package com.nsw.cs.registrations.config

import com.nsw.cs.registrations.utils.Constants.Companion.REGISTRATIONS
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Configuration class enabling cache features and creating cache components such as the [KeyGenerator] and [CacheManager].
 *
 * @see EnableCaching
 * @see CacheManager
 */
@Configuration
@EnableCaching
class CacheConfig : CachingConfigurerSupport() {
    @Bean
    override fun cacheManager(): CacheManager? {
        return ConcurrentMapCacheManager(REGISTRATIONS)
    }

    /**
     * Returns a [KeyGenerator] instance that can be used by the implementation cache to store elements.

     * @return KeyGenerator instance
     */
    @Bean
    override fun keyGenerator(): KeyGenerator {
        return KeyGenerator { target, method, params ->
            val builder = StringBuilder()
            builder.append("${target.javaClass.simpleName}-")
                    .append("${method.name}-")
            for (param in params) {
                builder.append("$param-")
            }
            builder.toString().toLowerCase()
        }
    }
}
