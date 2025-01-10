package com.innovatrix.ahaar.repository;

import com.innovatrix.ahaar.model.ApplicationUser;
import com.innovatrix.ahaar.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    /**
 * Retrieves a refresh token from the database by its token string.
 *
 * @param token the unique token string to search for
 * @return an Optional containing the RefreshToken if found, or an empty Optional if no matching token exists
 */
Optional<RefreshToken> findByToken(String token);

    /**
 * Retrieves a refresh token associated with a specific user.
 *
 * @param byUserName the application user for whom the refresh token is being searched
 * @return an Optional containing the RefreshToken if found for the given user, or an empty Optional otherwise
 */
Optional<RefreshToken> findByUser(ApplicationUser byUserName);
}
