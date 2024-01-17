package org.byovsiannikov.sticky_notes.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.byovsiannikov.sticky_notes.model.Token;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Function;

public class RefreshTokenJWESerializer implements Function<Token, String> {

    private final JWEEncrypter jweEncrypter;
    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    public RefreshTokenJWESerializer(JWEEncrypter jweEncrypter, JWEAlgorithm jweAlgorithm, EncryptionMethod encryptionMethod) {
        this.jweEncrypter = jweEncrypter;
        this.jweAlgorithm = jweAlgorithm;
        this.encryptionMethod = encryptionMethod;
    }

    public RefreshTokenJWESerializer(JWEEncrypter jweEncrypter) {
        this.jweEncrypter = jweEncrypter;
    }

    public void setJweAlgorithm(JWEAlgorithm jweAlgorithm) {
        this.jweAlgorithm = jweAlgorithm;
    }

    public void setEncryptionMethod(EncryptionMethod encryptionMethod) {
        this.encryptionMethod = encryptionMethod;
    }

    @Override
    public String apply(Token token) {
        JWEHeader header = new JWEHeader.Builder(jweAlgorithm,encryptionMethod)
                .keyID(token.id().toString())
                .build();


        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("C_Authorieties", token.authorities())
                .build();

        EncryptedJWT signedJWT = new EncryptedJWT(header, payload);

        try {
            signedJWT.encrypt(jweEncrypter);
            return signedJWT.serialize();
        } catch (JOSEException exception) {
            LoggerFactory.getLogger(RefreshTokenJWESerializer.class).error(exception.getMessage(),exception);
            return null;
        }
    }
}
