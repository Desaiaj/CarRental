/**
 * 
 */
package ca.sheridancollege.carrental.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Dont use code in the production system
		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.authorizeRequests().antMatchers(HttpMethod.POST, "/register").permitAll().antMatchers("/secure/**")
				.hasAnyRole("USER","ADMIN","EMPLOYEE").antMatchers("/Admin/*").hasRole("ADMIN")
				.antMatchers("/", "/js/**", "/css/**", "/images/**", "/**").permitAll().antMatchers("/h2-console/**")
				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.oauth2Login().loginPage("/oauth2/authorization/google").permitAll().defaultSuccessUrl("/loginSuccess")
				  .failureUrl("/permission-denied").and().logout()
				.invalidateHttpSession(true).clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.permitAll()
				// a tiny bit more to place below here in a few slides
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
