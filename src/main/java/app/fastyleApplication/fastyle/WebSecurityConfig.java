package app.fastyleApplication.fastyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import app.fastyleApplication.fastyle.services.ClienteService;
import app.fastyleApplication.fastyle.services.UsuarioService;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    //Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(resources).permitAll().antMatchers("/").permitAll()
				.antMatchers("/citaCrear/**", "/misCitas/**").access("hasRole('CLIENTE')")
				.antMatchers("/crearServicioEstetico/**", "/servicioEsteticoRegistro/**").access("hasRole('ADMIN')")
				.antMatchers("/misCitasEsteticista/**").access("hasRole('ESTETICISTA')")
				.antMatchers("/signup").permitAll()
				.anyRequest().permitAll()
				.and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/loginCorrecto")
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
	UsuarioService clienteService;

	// Registra el service para usuarios y el encriptador de contrasena
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(clienteService).passwordEncoder(passwordEncoder());
	}
	
	  @Bean
	  public UserDetailsService userDetailsService() {
	    return new UsuarioService();
	  };
	  
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	  }
	
}

