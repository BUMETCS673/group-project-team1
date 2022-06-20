package edu.bu.metcs673.trackr.security;

import static edu.bu.metcs673.trackr.common.CommonConstants.JWT_COOKIE_NAME;
import static edu.bu.metcs673.trackr.common.CommonConstants.USER_LOGGED_IN_COOKIE_NAME;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import edu.bu.metcs673.trackr.user.TrackrUserServiceImpl;

/**
 * Created to specify the spring-security behavior for authorization and
 * authentication related to the JWT tokens
 * <p>
 * Reference:
 * https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970
 *
 * @author Tim Flucker
 */
@Configuration
//@EnableWebSecurity
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
		http
			.csrf(csrf -> {
					// enable CSRF protection, ignore the following paths which are entry/exit
					// points, Spring Actuator endpoints, or API paths
					csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.ignoringAntMatchers("/api/**")
					.ignoringAntMatchers("/register")
					.ignoringAntMatchers("/login")
					.ignoringAntMatchers("/actuator/**");
			})
			.httpBasic()
			.disable()
			.cors()
			.and()
			.userDetailsService(userServiceImpl)
			.exceptionHandling()
			.authenticationEntryPoint( (request, response, authException) -> response.sendError(SC_UNAUTHORIZED, "UNAUTHORIZED"))
			.and()
			.sessionManagement()
			.sessionCreationPolicy(STATELESS);
		
		// Configure logout
		http.logout(logout -> logout.logoutUrl("/logout")
				.logoutSuccessHandler((request, response, authentication) -> response.setStatus(SC_OK))
				.deleteCookies(JWT_COOKIE_NAME, USER_LOGGED_IN_COOKIE_NAME)
				.invalidateHttpSession(true));

		// this will enable the frames for the H2 console
		http.headers().frameOptions().disable();

		// specifies when JWT filer is called in relation to other filters
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}