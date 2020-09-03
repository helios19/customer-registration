package com.nsw.cs.registrations.common.security

import org.owasp.esapi.ESAPI
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

/**
 * Wrapper class defining XSS regex expressions to be used for sanitizing the request parameters.
 * <p>
 * <p>Note that this class makes use of {@link ESAPI} library for encoding header and request values.</p>
 *
 * @see ESAPI
 * @see XssFilter
 */
class XssRequestWrapper(servletRequest: HttpServletRequest) : HttpServletRequestWrapper(servletRequest) {
    companion object {
        private val patterns = arrayOf<Pattern>(
                // Script fragments
                Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
                // src='...'
                Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL), Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL),
                // lonely script tags
                Pattern.compile("</script>", Pattern.CASE_INSENSITIVE), Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL),
                // eval(...)
                Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL),
                // expression(...)
                Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL),
                // javascript:...
                Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
                // vbscript:...
                Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
                // onload(...)=...
                Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL))
    }

    override fun getParameterValues(parameter: String): Array<String?> {
        val values = super.getParameterValues(parameter) ?: return null!!
        val count = values.size
        val encodedValues = arrayOfNulls<String>(count)
        for (i in 0 until count) {
            encodedValues[i] = stripXSS(values[i])
        }
        return encodedValues
    }

    override fun getParameter(parameter: String): String {
        val value = super.getParameter(parameter)
        return stripXSS(value)
    }

    override fun getHeader(name: String): String {
        val value = super.getHeader(name)
        return stripXSS(value)
    }

    private fun stripXSS(value: String?): String {
        var returnValue: String = ""
        if (value != null) {
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
            // avoid encoded attacks.
            returnValue = ESAPI.encoder().canonicalize(value)
            // Avoid null characters
            returnValue = value.replace(("").toRegex(), "")
            // Remove all sections that match a pattern
            for (scriptPattern in patterns) {
                returnValue = scriptPattern.matcher(value).replaceAll("")
            }
        }
        return returnValue
    }
}