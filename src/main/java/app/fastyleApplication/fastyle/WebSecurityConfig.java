package app.fastyleApplication.fastyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import app.fastyleApplication.fastyle.services.ClienteService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/resources/**", "/webjars/**", "/h2-console/**").permitAll()
//				.antMatchers(HttpMethod.GET, "/", "/oups").permitAll().antMatchers("/users/new").permitAll()
//				.anyRequest().denyAll().and().formLogin()
//				/* .loginPage("/login") */
//				.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");

//		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/console/**")
//		.permitAll();

		http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests().antMatchers("/signup")
				.permitAll().and().authorizeRequests().antMatchers("/citaCrear/**")
				.authenticated().and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/loginCorrecto")
				.failureUrl("/loginError").and().logout()
                .permitAll()
                .logoutSuccessUrl("/");;
		http.csrf().disable();
		http.headers().frameOptions().disable();

		http.requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure();

	}

	BCryptPasswordEncoder bCryptPasswordEncoder;

	// Crea el encriptador de contraseñas
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
//El numero 4 representa que tan fuerte quieres la encriptacion.
//Se puede en un rango entre 4 y 31. 
//Si no pones un numero el programa utilizara uno aleatoriamente cada vez
//que inicies la aplicacion, por lo cual tus contrasenas encriptadas no funcionaran bien
		return bCryptPasswordEncoder;
	}

	@Autowired
	ClienteService clienteService;

	// Registra el service para usuarios y el encriptador de contrasena
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(clienteService).passwordEncoder(passwordEncoder());
	}
}
