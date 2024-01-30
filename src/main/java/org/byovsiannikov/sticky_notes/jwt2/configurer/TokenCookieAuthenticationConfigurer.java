package org.byovsiannikov.sticky_notes.jwt2.configurer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class TokenCookieAuthenticationConfigurer
        extends AbstractHttpConfigurer<TokenCookieAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
    }
}
