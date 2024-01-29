package org.byovsiannikov.sticky_notes.jwt1.serializeAnddeserialize;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.byovsiannikov.sticky_notes.model.Token;

import java.util.Date;
import java.util.function.Function;

import static org.slf4j.LoggerFactory.getLogger;


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
            getLogger(AccsessTokenSerializer.class).error(e.getMessage(), e);
        }
        return null;
    }
}
