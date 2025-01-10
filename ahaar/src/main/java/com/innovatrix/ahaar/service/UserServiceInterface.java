package com.innovatrix.ahaar.service;

import com.innovatrix.ahaar.model.ApplicationUser;
import com.innovatrix.ahaar.DTO.ApplicationUserDTO;
import com.innovatrix.ahaar.DTO.LoginDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserServiceInterface {
    /**
 * Retrieves a paginated list of application users.
 *
 * @param page The page number to retrieve (zero-indexed)
 * @param size The number of users to return per page
 * @return A {@code Page} containing a subset of {@code ApplicationUser} objects
 * @throws IllegalArgumentException If page or size is negative
 */
Page<ApplicationUser> getUsers(int page, int size);

    Optional<ApplicationUser> addUser(ApplicationUserDTO user);

    ApplicationUser updateUser(Long userId, ApplicationUserDTO user);

    void deleteUser(Long id);

    /**
 * Retrieves a user by their unique identifier.
 *
 * @param id The unique identifier of the user to retrieve
 * @return An Optional containing the ApplicationUser if found, or an empty Optional if no user exists with the given ID
 */
Optional<ApplicationUser> getUserById(Long id);

    /**
 * Authenticates a user with the provided login credentials.
 *
 * @param loginDTO The data transfer object containing user login credentials
 * @return A string representing the authentication result, typically an authentication token or status message
 * @throws AuthenticationException If login credentials are invalid or authentication fails
 */
String login(LoginDTO loginDTO);
}

