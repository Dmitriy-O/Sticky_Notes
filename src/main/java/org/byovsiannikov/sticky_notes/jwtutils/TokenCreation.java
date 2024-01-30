package org.byovsiannikov.sticky_notes.jwtutils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import org.byovsiannikov.sticky_notes.dto.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
public class TokenCreation {
    @Value("${jwt.cookie-token-key}")
    private String tokenSecretParts;
    @Value("${jwt.lifetime}")
    private Duration tokenLiveTime;

    //DIR uses a shared symmetric key, meaning both sender and receiver must possess the same secret key.
    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
    private static Logger logger = LoggerFactory.getLogger(TokenCreation.class);

    //encrypt for header
    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;
    private DirectEncrypter encrypter;
    private DirectDecrypter decrypter;


    public String serializeToken(UserDetails userDetails) throws ParseException, JOSEException {
        List<String> rolesList = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
//        Date instant=new Date();
//        Date expiresAt=new Date(Instant.now().get()+tokenLiveTime.toMillis());
        JWEHeader jweHeader = new JWEHeader(jweAlgorithm, encryptionMethod);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim("roles", rolesList) //payload
                .subject(userDetails.getUsername())
                .issueTime(Date.from(Instant.now()))
                .expirationTime(Date.from(Instant.now().plus(tokenLiveTime)))
                .build();


//        This line creates an instance of the DirectEncrypter class,
//        which is a specialized encrypter for JWE objects using the Direct Key Agreement (DIR) algorithm.
        encrypter = new DirectEncrypter(OctetSequenceKey.parse(tokenSecretParts));
        DirectEncrypter directEncrypter = encrypter;

        EncryptedJWT encryptedJWT = new EncryptedJWT(jweHeader, claimsSet);
        encryptedJWT.encrypt(directEncrypter);


        return encryptedJWT.serialize();


    }

    public String getSubject(String token) throws ParseException, JOSEException {
        try {
            EncryptedJWT parsed = EncryptedJWT.parse(token);
            decrypter = new DirectDecrypter(
                    OctetSequenceKey.parse(tokenSecretParts)
            );
            parsed.decrypt(decrypter);
            JWTClaimsSet claimsSet = parsed.getJWTClaimsSet();

            return claimsSet.getSubject();
        } catch (ParseException | JOSEException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
    public List<String> getRoles(String token) throws ParseException, JOSEException {
        try {
            EncryptedJWT parsed = EncryptedJWT.parse(token);
            decrypter = new DirectDecrypter(
                    OctetSequenceKey.parse(tokenSecretParts)
            );
            parsed.decrypt(decrypter);
            JWTClaimsSet claimsSet = parsed.getJWTClaimsSet();

            return claimsSet.getStringListClaim("roles");
        } catch (ParseException | JOSEException e) {
            logger.error(e.getMessage(), e);

        }
        return null;
    }
    public Token getAllClaims(String token) throws ParseException, JOSEException {
        try {
            EncryptedJWT parsed = EncryptedJWT.parse(token);
            decrypter = new DirectDecrypter(
                    OctetSequenceKey.parse(tokenSecretParts)
            );
            parsed.decrypt(decrypter);
            JWTClaimsSet claimsSet = parsed.getJWTClaimsSet();

            return new Token(UUID.fromString(claimsSet.getJWTID()),
                    claimsSet.getSubject(), claimsSet.getStringListClaim("roles"),
                    claimsSet.getIssueTime().toInstant(), claimsSet.getExpirationTime().toInstant());
        } catch (ParseException | JOSEException e) {
            logger.error(e.getMessage(), e);

        }
        return null;
    }
}
