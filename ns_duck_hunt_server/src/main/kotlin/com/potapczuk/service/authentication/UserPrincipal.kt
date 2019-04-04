package com.potapczuk.service.authentication

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(
        val id: Long,
        private val username: String,
        private val password: String,
        private val deleted: Boolean
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return listOf(SimpleGrantedAuthority("ROLE_USER"))
    }


    override fun getPassword(): String {
        return password
    }


    override fun getUsername(): String {
        return username
    }


    override fun isAccountNonExpired(): Boolean {
        return !deleted
    }


    override fun isAccountNonLocked(): Boolean {
        return !deleted
    }


    override fun isCredentialsNonExpired(): Boolean {
        return !deleted
    }


    override fun isEnabled(): Boolean {
        return !deleted
    }
}