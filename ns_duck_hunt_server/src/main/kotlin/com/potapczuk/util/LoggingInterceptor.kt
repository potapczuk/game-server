package com.potapczuk.util

import org.apache.juli.logging.LogFactory
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoggingInterceptor : HandlerInterceptorAdapter() {

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex:
    Exception?) {
        val logMessage = StringBuilder()
        logMessage.append("method: ").append(request!!.method).append("\t")
        logMessage.append("uri: ").append(request.requestURI).append("\t")
        logMessage.append("status: ").append(response!!.status).append("\t")
        logMessage.append("remoteAddress: ").append(request.remoteAddr).append("\t")

        if (ex != null) {
            LoggingInterceptor.LOG.error(logMessage.toString(), ex)
        } else {
            LoggingInterceptor.LOG.info(logMessage.toString())
        }
    }

    companion object {
        private val LOG = LogFactory.getLog(LoggingInterceptor::class.java)
    }
}
