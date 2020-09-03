package com.nsw.cs.registrations.common.security

import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest


/**
 * [javax.servlet.Filter] that handles potential XSS attacks added to request parameters.
 * This filter will primarily be used to sanitize (encode and strip out malicious block of code)
 * the parameters sent to the server.

 * @see XssRequestWrapper
 */
class XssFilter : Filter {

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig?) {
    }

    override fun destroy() {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        chain.doFilter(XssRequestWrapper(request as HttpServletRequest), response)
    }

}