package org.byovsiannikov.sticky_notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//Enables login form
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

//    HttpSecurity provides methods to configure security for a specific resource
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.
//        return httpSecurity.authorizeHttpRequests()
        return null;
    }

    @Bean
    public PasswordEncoder customPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //It returns this AuthenticationManager instance as the created bean.
    //AuthenticationManager, returns fully populated authentication object


    //    ProviderManager is the most commonly used implementation of AuthenticationManager. ProviderManager delegates to a List of AuthenticationProvider instances. Each AuthenticationProvider has an opportunity to indicate that authentication should be successful, fail, or indicate it cannot make a decision and allow a downstream AuthenticationProvider to decide. If none of the configured AuthenticationProvider instances can authenticate, authentication fails with a ProviderNotFoundException, which is a special AuthenticationException that indicates that the ProviderManager was not configured to support the type of Authentication that was passed into it.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(customPasswordEncoder());

        return daoAuthenticationProvider;
    }

}
