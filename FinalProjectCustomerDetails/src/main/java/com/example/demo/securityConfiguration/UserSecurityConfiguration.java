package com.example.demo.securityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class UserSecurityConfiguration extends WebSecurityConfigurerAdapter {
    //Authentication of User
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("zorba_admin").password(passwordEncoder().encode("zorbaadmin001")).roles("ADMIN")
                .and()
                .withUser("zorba_user").password(passwordEncoder().encode("zorbauser002")).roles("USER");

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/final/savaData").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/final/fetchCustomerInfo").hasAnyRole("ADMIN","USER")
                .antMatchers(HttpMethod.PUT,"/final/updateCustomerInfo").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/final/deleteRecords").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
