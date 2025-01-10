package com.innovatrix.ahaar.service;

import com.innovatrix.ahaar.model.ApplicationUser;
import com.innovatrix.ahaar.model.RefreshToken;
import com.innovatrix.ahaar.repository.RefreshTokenRepository;
import com.innovatrix.ahaar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;

    private static final long TIMEOUT = 1000 * 60 * 5; // 5 minute
    @Autowired
    private UserService userService;

    /**
     * Creates or retrieves a refresh token for a given username.
     *
     * This method handles the lifecycle of refresh tokens by:
     * - Checking if the user exists
     * - Returning an existing valid token if present
     * - Deleting expired tokens
     * - Generating a new refresh token with a 5-minute expiration
     *
     * @param username The username for which to create or retrieve a refresh token
     * @return A valid RefreshToken associated with the user
     * @throws RuntimeException if the user is not found in the system
     */
    public RefreshToken createRefreshToken(String username) {
        Optional<ApplicationUser> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Optional<RefreshToken> previousToken = refreshTokenRepository.findByUser(user.get());

        if(previousToken.isPresent() && previousToken.get().getExpiryDate().isAfter(Instant.now())) {
            return previousToken.get();
        }

        if(previousToken.isPresent() && previousToken.get().getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(previousToken.get());
        }
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByUserName(username).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(TIMEOUT))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }


    /**
     * Retrieves a refresh token from the repository based on the provided token string.
     *
     * @param token the unique token string to search for
     * @return an Optional containing the RefreshToken if found, or an empty Optional if no matching token exists
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }


    /**
     * Verifies the expiration status of a refresh token.
     *
     * @param token the refresh token to verify
     * @return the original refresh token if it is still valid
     * @throws RuntimeException if the refresh token has expired
     *
     * This method checks whether the provided refresh token has passed its expiry date.
     * If the token is expired, it is deleted from the repository and a runtime exception is thrown,
     * indicating that a new sign-in request is required. If the token is still valid,
     * the original token is returned.
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}

