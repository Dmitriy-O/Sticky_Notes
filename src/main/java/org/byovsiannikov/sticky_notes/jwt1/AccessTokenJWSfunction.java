package org.byovsiannikov.sticky_notes.jwt1;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.byovsiannikov.sticky_notes.dto.Token;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Function;

public class AccessTokenJWSfunction implements Function<Token, String> {
    private JWSSigner signer;
    private JWSAlgorithm algorithm = JWSAlgorithm.HS256;

    public void setSigner(JWSSigner signer) {
        this.signer = signer;
    }

    public AccessTokenJWSfunction(JWSSigner signer, JWSAlgorithm algorithm) {
        this.signer = signer;
        this.algorithm = algorithm;
    }

    public AccessTokenJWSfunction(JWSSigner signer) {
        this.signer = signer;
    }


    @Override
    public String apply(Token token) {
        JWSHeader header = new JWSHeader.Builder(algorithm)
                .keyID(token.id().toString())
                .build();


        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("C_Authorieties", token.authorities())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, payload);

        try {
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (JOSEException exception) {
          LoggerFactory.getLogger(AccessTokenJWSfunction.class).error(exception.getMessage(),exception);
          return null;
        }
    }
}
