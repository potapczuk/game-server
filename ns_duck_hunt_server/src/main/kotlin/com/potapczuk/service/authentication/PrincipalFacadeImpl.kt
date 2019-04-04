package com.potapczuk.service.authentication

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class PrincipalFacadeImpl : PrincipalFacade {
    override fun getPrincipal(): UserPrincipal? {
        return if (SecurityContextHolder.getContext().authentication.principal != "anonymousUser")
            SecurityContextHolder.getContext().authentication.principal as UserPrincipal
        else
            null
    }
}