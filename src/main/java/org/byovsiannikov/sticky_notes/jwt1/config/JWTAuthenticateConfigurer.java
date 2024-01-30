package org.byovsiannikov.sticky_notes.jwt1.config;

import org.byovsiannikov.sticky_notes.jwt1.filters.RequestJWTTokenFilter;
import org.byovsiannikov.sticky_notes.dto.Token;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.function.Function;

/**
 * Register and Configure custom filters
 */
public class JWTAuthenticateConfigurer extends AbstractHttpConfigurer<JWTAuthenticateConfigurer,HttpSecurity> {
    private Function<Token, String> refreshTokenStringSerializer = Object::toString;

    private Function<Token, String> accessTokenStringSerializer = Object::toString;

    private Function<String, Token> accessTokenStringDeserializer;

    private Function<String, Token> refreshTokenStringDeserializer;

    private JdbcTemplate jdbcTemplate;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        CsrfConfigurer csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if (csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/jwt/tokens", "POST"));
        }
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        //register filters
        RequestJWTTokenFilter requestJwtTokensFilter = new RequestJWTTokenFilter();
        requestJwtTokensFilter.setAccessTokenStringSerializer(this.accessTokenStringSerializer);
        requestJwtTokensFilter.setRefreshTokenStringSerializer(this.refreshTokenStringSerializer);

//        var jwtAuthenticationFilter = new AuthenticationFilter(builder.getSharedObject(AuthenticationManager.class),
//                new JwtAuthenticationConverter(this.accessTokenStringDeserializer, this.refreshTokenStringDeserializer));
//        jwtAuthenticationFilter
//                .setSuccessHandler((request, response, authentication) -> CsrfFilter.skipRequest(request));
//        jwtAuthenticationFilter
//                .setFailureHandler((request, response, exception) -> response.sendError(HttpServletResponse.SC_FORBIDDEN));
//
//        var authenticationProvider = new PreAuthenticatedAuthenticationProvider();
//        authenticationProvider.setPreAuthenticatedUserDetailsService(
//                new TokenAuthenticationUserDetailsService(this.jdbcTemplate));
//
//        var refreshTokenFilter = new RefreshTokenFilter();
//        refreshTokenFilter.setAccessTokenStringSerializer(this.accessTokenStringSerializer);
//
//        var jwtLogoutFilter = new JwtLogoutFilter(this.jdbcTemplate);

//        builder.addFilterAfter(requestJwtTokensFilter, ExceptionTranslationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter, CsrfFilter.class)
//                .addFilterAfter(refreshTokenFilter, ExceptionTranslationFilter.class)
//                .addFilterAfter(jwtLogoutFilter, ExceptionTranslationFilter.class)
//                .authenticationProvider(authenticationProvider);
    }

    public JWTAuthenticateConfigurer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public JWTAuthenticateConfigurer refreshTokenStringSerializer(
//            Function<Token, String> refreshTokenStringSerializer) {
//        this.refreshTokenStringSerializer = refreshTokenStringSerializer;
//        return null;
//    }
}
