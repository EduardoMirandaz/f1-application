package SCC0541.F1Backend.security;

import SCC0541.F1Backend.config.PostgresMD5PasswordEncoder;
import SCC0541.F1Backend.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration{

    private final TokenService tokenService;
    private final LogService logService;

    private final String[] caminhosIgnorados = new String[]{"/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/usuario/login",
            "/usuario-controller/login"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/usuario/listar-usuarios").hasAuthority("Administrador")
                        .requestMatchers("/airports/relatorios/exibir-aeroportos-proximos").hasAuthority("Administrador")
                        .requestMatchers("/drivers/create").hasAuthority("Administrador")
                        .requestMatchers("/constructors/create").hasAuthority("Administrador")
                        .requestMatchers("/status/relatorios/quantidade-por-resultado")
                            .hasAnyAuthority("Administrador", "Escuderia", "Piloto")
                        .requestMatchers("/usuario/login").permitAll()
                        .requestMatchers(caminhosIgnorados).permitAll()
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(
                new TokenAuthenticationFilter(tokenService, logService),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
<<<<<<< HEAD
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:3000")
                        .exposedHeaders("Authorization");
            }
        };
=======
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://172.21.0.2:3000"));
        config.setAllowedMethods(Arrays.asList("GET","POST"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
>>>>>>> ececf303121b27cc2030f95a04647c5df9caddd5
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/",
                "/usuario/login",
                "/swagger-ui/index.html#/usuario-controller/login"
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PostgresMD5PasswordEncoder();
    }

}
