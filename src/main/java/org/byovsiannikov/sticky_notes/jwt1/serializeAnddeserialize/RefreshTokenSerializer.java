package org.byovsiannikov.sticky_notes.jwt1.serializeAnddeserialize;

import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.byovsiannikov.sticky_notes.model.Token;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.function.Function;

@AllArgsConstructor
public class RefreshTokenSerializer implements Function<Token, String> {
    private JWEEncrypter encrypter;
    @Setter
    private JWEAlgorithm jwsAlgorithm = JWEAlgorithm.DIR;
    @Setter
    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;




    @Override
    public String apply(Token token) {
        JWEHeader jweHeader = new JWEHeader.Builder(jwsAlgorithm, encryptionMethod)
                .keyID(token.id().toString())
                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();

        EncryptedJWT encryptedJWT = new EncryptedJWT(jweHeader,jwtClaimsSet);
        try {
            encryptedJWT.encrypt(encrypter);
            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            LoggerFactory.getLogger(AccsessTokenSerializer.class).error(e.getMessage(), e);
        }
        return null;
    }
}
