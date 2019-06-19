package com.greenapper.config;

import com.greenapper.services.impl.security.DefaultUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DefaultUserDetailsService userDetailsService;

	@Bean
	public PostLoginRedirectHandler redirectHandler() {
		return new PostLoginRedirectHandler();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/home", "/login").permitAll()
				.antMatchers("/campaign-manager/**").access("hasRole('CAMPAIGN_MANAGER')")
				.antMatchers("/campaigns").access("hasRole('CAMPAIGN_MANAGER')")
				.and()
				.formLogin().loginPage("/login").loginProcessingUrl("/login").failureUrl("/login-error")
				.and()
				.logout().permitAll().logoutSuccessUrl("/home");
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new SCryptPasswordEncoder((int) Math.pow(2, 14), 8, 8, 64, 64);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
}