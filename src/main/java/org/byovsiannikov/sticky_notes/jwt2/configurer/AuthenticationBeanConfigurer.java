package org.byovsiannikov.sticky_notes.jwt2.configurer;

import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.byovsiannikov.sticky_notes.jwt2.GetCSRFTokenFilter;
import org.byovsiannikov.sticky_notes.jwt2.serializers.TokenCookieSerializer;
import org.byovsiannikov.sticky_notes.jwt2.strategies.TokenCookieAuthenticationStrategyImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

//@Configuration
public class AuthenticationBeanConfigurer {

    @Bean
    public TokenCookieSerializer tokenCookieSerializer(@Value("${jwt.cookie-token-key}") String cookieTokenKey) throws Exception {
        return new TokenCookieSerializer(new DirectEncrypter
                (OctetSequenceKey.parse(cookieTokenKey)));
    }

    @Bean
    public TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer() {
        return new TokenCookieAuthenticationConfigurer();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   TokenCookieAuthenticationConfigurer
                                       tokenCookieAuthenticationConfigurer,
                                                   TokenCookieSerializer tokenCookieSerializer
    ) throws Exception {
        TokenCookieAuthenticationStrategyImpl tokenCookieAuthenticationStrategy=new TokenCookieAuthenticationStrategyImpl();
        tokenCookieAuthenticationStrategy.setTokenSerializer(tokenCookieSerializer);

        httpSecurity.httpBasic(Customizer.withDefaults())
                .addFilterAfter(new GetCSRFTokenFilter(), ExceptionTranslationFilter.class)
                .authorizeHttpRequests(authenticationRequests ->
                        authenticationRequests.requestMatchers("/login").authenticated()
                                .requestMatchers("/any").permitAll()
                                .anyRequest().authenticated()
                ).sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .sessionAuthenticationStrategy(tokenCookieAuthenticationStrategy))
                //comment: stores session in cookies
                //You can persist the CsrfToken in a cookie to support a JavaScript-based application using the CookieCsrfTokenRepository.
                //The CookieCsrfTokenRepository writes to a cookie named XSRF-TOKEN
                // and reads it from an HTTP request header named X-XSRF-TOKEN
                // or the request parameter _csrf by default. These defaults come from Angular and its predecessor AngularJS.
                .csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository())
                        //creates xsrf_ token in web
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        //dont create new xsrf token in each new authentication
                        .sessionAuthenticationStrategy((authentication, request, response) -> {
                        }));
        //creates  csrf_ token in web

        httpSecurity.apply(tokenCookieAuthenticationConfigurer); //??
//        httpSecurity.with(tokenCookieAuthenticationConfigurer,tokenCookieAuthenticationConfigurer1 -> {});
        return httpSecurity.build();
    }

    //The Authentication contains:
    //
    //principal: Identifies the user. When authenticating with a username/password this is often an instance of UserDetails.
    //
    //credentials: Often a password. In many cases, this is cleared after the user is authenticated, to ensure that it is not leaked.
    //
    //authorities: The GrantedAuthority instances are high-level permissions the user is granted. Two examples are roles and scopes.

    //Credentionals - password
    //Principal - username


//    UserDetails
//    username, a password, and other attributes for authenticating with a username and password.


    //    The RequestAttributeSecurityContextRepository holds the entire SecurityContext object for the current request. This includes:
//    Authenticated principal (logged-in user)
//    Granted authorities (user's roles and permissions)
//            Authentication details (e.g., username, password, remember-me token)
//    Security token (e.g., JWT) if using one
//    Other context information specific to your application
    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        //username is a result
        return username -> jdbcTemplate.query("select * from t_user where c_username=?",
                (rs, i) -> User.builder()
                        //This is a RowMapper function that maps each row from the result set (rs) to a User object.
                        .username(rs.getString("c_username"))
                        .password(rs.getString("c_password"))
                        .authorities(
                                jdbcTemplate.query("select c_authorities from t_user_authority where id_user = ?",
                                        (rs1, i1) ->
                                                new SimpleGrantedAuthority(rs1.getString("c_authority")),
                                        rs.getInt("id")))
                        .build(), username).stream().findFirst().orElse(null);
    }
}
