package edu.rit.iste500.dubai.RapidsCemeteryAPI.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.rit.iste500.dubai.RapidsCemeteryAPI.service.UserService;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.SWAGGER_LOGIN_URL).permitAll()
				.antMatchers(HttpMethod.POST, SecurityConstants.FORGOTTEN_PASSWORD).permitAll()
				.antMatchers("/api-docs", "/v2/api-docs", "/configuration/ui", "/swagger-resources",
						"/configuration/security", "/swagger-ui.html", "/webjars/**",
						"/swagger-resources/configuration/ui", "/api/item/*", "/login/*", "/api/tour/*", "/api/mail/*",
						"/api/stop/*",
						"/api/tag/*")
				.permitAll().anyRequest().authenticated().and()
				.addFilterBefore(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilter(jwtAuthorizationFilter()).sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder shaPasswordEncoder = new BCryptPasswordEncoder();
		return shaPasswordEncoder;
	}

	@Bean
	public JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
		JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager());
		return jwtAuthorizationFilter;
	}

	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		// daoAuthenticationProvider.setSaltSource(reflectionSaltSource());
		return daoAuthenticationProvider;
	}
}
