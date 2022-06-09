package com.andymcg.northumberlandbadmintonadmin

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.test.context.ActiveProfiles

@TestConfiguration
@ActiveProfiles("api-test")
class AllowAnonymousWebAccess : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    public override fun configure(web: HttpSecurity) {
        web.antMatcher("**/*").anonymous()
    }
}