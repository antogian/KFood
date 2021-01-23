package com.antogian.athena.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
                ?.inMemoryAuthentication()
                ?.withUser("user")?.password(passwordEncoder().encode("user"))?.roles("USER")
                ?.and()
                ?.withUser("admin")?.password(passwordEncoder().encode("admin"))?.roles("ADMIN")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity?) {
        http
                ?.authorizeRequests()
//                ?.antMatchers("/menu")?.hasAnyRole("USER", "ADMIN")
                ?.antMatchers("/*")?.permitAll()
                ?.and()?.formLogin()?.loginPage("/login")
    }
}