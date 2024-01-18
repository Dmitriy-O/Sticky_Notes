package org.byovsiannikov.sticky_notes.jwt.serializeAnddeserialize;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.byovsiannikov.sticky_notes.model.Token;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Function;


public class AccsessTokenSerializer implements Function<Token, String> {
    private JWSSigner jwsSigner;
    private JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public String apply(Token token) {
        JWSHeader jwsHeader = new JWSHeader.Builder(jwsAlgorithm)
                .keyID(token.id().toString())
                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
        try {
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            LoggerFactory.getLogger(AccsessTokenSerializer.class).error(e.getMessage(), e);
        }
        return null;
    }
}
