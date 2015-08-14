package org.sistcoop.rrhh.services.resources;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AuthenticationService {

    public final static String apiKey_id = "144JVZINOF5EBNCMG9EXAMPLE";
    public final static String apiKey_secret = "lWxOiKqKPNwJmSldbiSkEbkNjgh2uRSNAb+AEXAMPLE";

    // Sample method to construct a JWT
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey_secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    // Sample method to validate and read the JWT
    public static Claims parseJWT(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as
        // expected)
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(apiKey_secret))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static boolean authenticate(String authCredentials) {
        Claims claims = parseJWT(authCredentials);
        if (claims != null)
            return true;
        else
            return false;
    }
}
