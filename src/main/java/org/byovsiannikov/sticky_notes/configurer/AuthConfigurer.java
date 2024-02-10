package org.byovsiannikov.sticky_notes.configurer;

import jakarta.servlet.FilterChain;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
//@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class AuthConfigurer {

    private final UserService userService;
    private final JWTRequestFilter myContextHolderFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
        return authenticationManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();

        daoProvider.setPasswordEncoder(passwordEncoder());
        //using loadByName
        daoProvider.setUserDetailsService(userService);

        return daoProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.POST,"/login").authenticated()
                                .requestMatchers(HttpMethod.POST,"/postNote").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/getInfo").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/getAdminInfo").hasAuthority("ROLE_ADMIN")
                                .anyRequest().permitAll())
                .sessionManagement(el -> el.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(el -> el.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(myContextHolderFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
