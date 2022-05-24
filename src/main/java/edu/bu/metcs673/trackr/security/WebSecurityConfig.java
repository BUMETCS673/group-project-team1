package edu.bu.metcs673.trackr.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.bu.metcs673.trackr.service.impl.TrackrUserServiceImpl;

/**
 * Created to specify the spring-security behavior for authorization and
 * authentication related to the JWT tokens
 * 
 * Reference:
 * https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970
 * 
 * @author Tim Flucker
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TrackrUserServiceImpl userServiceImpl;

	@Autowired
	private JwtFilter filter;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> userServiceImpl.loadUserByUsername(username));
	}

	/**
	 * Configures traffic through the application to expose one endpoint that
	 * everyone can access, then implementing an authentication entry point for all
	 * other API URLs. Configures the session as well to be STATELESS (best
	 * practice).
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().httpBasic().disable().cors().and().authorizeHttpRequests().antMatchers("/api/v1/users/**")
				.permitAll().and().userDetailsService(userServiceImpl).exceptionHandling()
				.authenticationEntryPoint((request, response, authException) -> response
						.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED"))
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		
		// this will enable the frames for the H2 console
		http.headers().frameOptions().disable();

		// specifies when JWT filer is called in relation to other filters
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}

}
