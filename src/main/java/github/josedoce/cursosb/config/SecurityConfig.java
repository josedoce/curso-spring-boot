package github.josedoce.cursosb.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import github.josedoce.cursosb.security.CustomAuthenticationManager;
import github.josedoce.cursosb.security.JWTAuthenticationFilter;
import github.josedoce.cursosb.security.JWTUtil;
import github.josedoce.cursosb.security.JwtAuthorizationFilter;
import github.josedoce.cursosb.security.handlers.CustomAccessDeniedHandler;
import github.josedoce.cursosb.services.UserDetailsServiceImpl;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	//https://stackoverflow.com/questions/72493425/how-to-update-deprecated-websecurityconfigureradapter-with-userdetailsservice-in
	//https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
	
	@Autowired
	private Environment env;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
	};
	
	private static final String[] PUBLIC_MATCHERS_POST = {
			"/login",
			"/clientes/**",
			"/auth/forgot/**"
	};
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			//libera o acesso ao h2
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		
		.anyRequest().authenticated();
		http.exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler())
		.authenticationEntryPoint(new EP());	
	
		http.addFilter(new JWTAuthenticationFilter(this.authenticationManager(), jwtUtil));
		http.addFilter(new JwtAuthorizationFilter(this.authenticationManager(), jwtUtil, userDetailsServiceImpl));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
	}
	@Bean
	AuthenticationManager authenticationManager() {
		return new CustomAuthenticationManager(userDetailsServiceImpl);
	}
	
	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	public static class EP implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			Map<String, String> payload = new HashMap<>();
			payload.put("erro", "É necessário autorização.");
			ObjectMapper obj = new ObjectMapper();
			response.setContentType("application/json;charset=UTF-8;");
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response
			.getWriter()
			.append(obj.writeValueAsString(payload));
		}
		
	}
	
}
