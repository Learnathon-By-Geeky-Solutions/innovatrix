package com.innovatrix.ahaar.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private String secretKey;

    /**
     * Constructs a new JWTService instance and initializes a secret key for JWT signing.
     *
     * This constructor generates a secure HMAC SHA-256 secret key and encodes it in Base64 format.
     * If the key generation fails due to an unsupported algorithm, a RuntimeException is thrown.
     *
     * @throws RuntimeException if the HMAC SHA-256 algorithm is not available
     */
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Decodes the base64-encoded secret key and generates an HMAC SHA key for token signing and verification.
     *
     * @return A SecretKey object used for cryptographic operations with HMAC SHA algorithm
     * @throws IllegalArgumentException if the secret key decoding fails
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JSON Web Token (JWT) for the specified username.
     *
     * @param username the username to include as the subject of the token
     * @return a compact, signed JWT string valid for 10 minutes
     * 
     * This method creates a JWT with the following characteristics:
     * - Subject set to the provided username
     * - Issued at the current timestamp
     * - Expiration set to 10 minutes from the current time
     * - Signed with a secret HMAC SHA-256 key
     * - Optional additional claims can be added to the token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<String, Object>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .and()
                .signWith(getKey())
                .compact()
                ;
    }

    /**
     * Extracts the username from a JSON Web Token (JWT).
     *
     * @param token the JWT from which to extract the username
     * @return the username (subject) contained in the token
     */
    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using a provided claim resolver function.
     *
     * @param <T> the type of the claim to be extracted
     * @param token the JWT token from which to extract the claim
     * @param claimResolver a function that resolves a specific claim from the token's claims
     * @return the extracted claim of type T
     * @throws JwtException if the token is invalid or cannot be parsed
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from a signed JSON Web Token (JWT).
     *
     * @param token The JWT from which to extract claims
     * @return Claims object containing all token payload information
     * @throws JwtException if the token is invalid, malformed, or signature verification fails
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates a JSON Web Token (JWT) against the provided user details.
     *
     * @param token The JWT to validate
     * @param userDetails The user details to compare against the token
     * @return true if the token is valid (username matches and token is not expired), false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Determines whether a JWT has expired by comparing its expiration date with the current date.
     *
     * @param token The JSON Web Token to check for expiration
     * @return {@code true} if the token has expired, {@code false} otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token from which to extract the expiration date
     * @return The expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
