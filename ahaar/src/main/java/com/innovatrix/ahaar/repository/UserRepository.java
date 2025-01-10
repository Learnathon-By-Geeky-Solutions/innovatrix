package com.innovatrix.ahaar.repository;

import com.innovatrix.ahaar.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    /**
 * Retrieves an ApplicationUser by their email address.
 *
 * @param email the email address to search for
 * @return an Optional containing the ApplicationUser if found, or an empty Optional if no user exists with the given email
 */
Optional<ApplicationUser> findByEmail(String email);

    /**
 * Retrieves an ApplicationUser by their username.
 *
 * @param userName the username to search for
 * @return an Optional containing the ApplicationUser if found, or an empty Optional if no user matches the given username
 */
Optional<ApplicationUser> findByUserName(String userName);
}
