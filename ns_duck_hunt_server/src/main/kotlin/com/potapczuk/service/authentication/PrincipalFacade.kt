package com.potapczuk.service.authentication

interface PrincipalFacade {
    
    /**
     * Get the logged in principal if exists
     */
    fun getPrincipal(): UserPrincipal?
}