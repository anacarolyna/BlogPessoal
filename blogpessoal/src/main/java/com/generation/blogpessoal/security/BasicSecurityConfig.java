package com.generation.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//notação de scurity
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//serve para comparar os dados digitados com os dados salvos no banco de dados
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override 
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService);   //usuario em memoria PARA TESTE
		auth.inMemoryAuthentication()
		.withUser("root")//não precisa estar cadastrado no banco de dados mas é um user válido
		.password(passwordEncoder().encode("root"))
		.authorities("ROLE_USER");// usuário válido
	}

	//notação que deixa uma função acessível globalmente(em toda a minha aplicação)
	@Bean // ter ou não ter depende da aplicação
	public PasswordEncoder passwordEncoder() { //como se fosse a classe main de segurança
		return new BCryptPasswordEncoder();
	}
	
	@Override// aqui seria o que você precisa testar
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()
		.antMatchers(HttpMethod.OPTIONS).permitAll()// para saber quais opções de metodos tenha acessiveis para utilizar
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors()
		.and().csrf().disable();


	}
	
}
