package org.byovsiannikov.sticky_notes.jwt.configurer;

import org.byovsiannikov.sticky_notes.jwt.GetCSRFTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
public class AuthenticationBeanConfigurer {
    @Bean
    public TokenCookieAuthenticationConfigurer tokenCookieAuthenticationConfigurer(){
        return new TokenCookieAuthenticationConfigurer();
    }
    @Bean
    public SecurityFilterChain (HttpSecurity httpSecurity,
                                TokenCookieAuthenticationConfigurer  tokenCookieAuthenticationConfigurer
                                ) throws Exception {
        httpSecurity.httpBasic(Customizer.withDefaults())
                .addFilterAfter(new GetCSRFTokenFilter(), ExceptionTranslationFilter.class)
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
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate){
        //username is a result
        return username ->jdbcTemplate.query("select * from t_user where c_username=?",
                (rs,i)-> User.builder()
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
