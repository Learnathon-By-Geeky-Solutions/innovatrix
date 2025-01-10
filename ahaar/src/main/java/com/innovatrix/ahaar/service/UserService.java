package com.innovatrix.ahaar.service;

import com.innovatrix.ahaar.model.ApplicationUser;
import com.innovatrix.ahaar.DTO.ApplicationUserDTO;
import com.innovatrix.ahaar.DTO.LoginDTO;
import com.innovatrix.ahaar.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private JWTService jwtService;

    private RedisService redisService;

    /**
     * Constructs a UserService with the necessary dependencies for user management and authentication.
     *
     * @param userRepository Repository for performing user data operations
     * @param authenticationManager Manages user authentication processes
     * @param bCryptPasswordEncoder Encodes user passwords for secure storage
     * @param jwtService Generates JSON Web Tokens for authenticated users
     * @param redisService Handles caching of user-related data
     */
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, JWTService jwtService, RedisService redisService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    /**
     * Retrieves a paginated list of users from the repository.
     *
     * @param page the page number to retrieve (zero-indexed)
     * @param size the number of users to return per page
     * @return a Page of ApplicationUser containing users for the specified page
     */
    public Page<ApplicationUser> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    private void checkConditions(ApplicationUserDTO user) {
        if(user.getUserName().length() <= 0) {
            throw new IllegalStateException("User name is empty");
        }
        if(user.getUserName().length() > 100) {
            throw new IllegalStateException("User name is too long, must be less than 100 characters");
        }
        if(user.getPassword().length() <= 0) {
            throw new IllegalStateException("Password is empty");
        }
        if (user.getEmail().length() <= 0) {
            throw new IllegalStateException("Email is empty");
        }
        if(user.getEmail().length() > 100) {
            throw new IllegalStateException("Email is too long, must be less than 100 characters");
        }
    }

    /**
     * Adds a new user to the system after performing validation and security checks.
     *
     * @param user The ApplicationUserDTO containing user registration details
     * @return An Optional containing the newly created ApplicationUser
     * @throws IllegalStateException if a user with the same email already exists or if user data fails validation
     */
    public Optional<ApplicationUser> addUser(ApplicationUserDTO user) {
        Optional<ApplicationUser> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("User with this email already exists");
        }
        checkConditions(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user.toEntity()));
    }

    /**
     * Updates an existing user's information in the system.
     *
     * @param userId The unique identifier of the user to be updated
     * @param user The data transfer object containing updated user information
     * @return The updated ApplicationUser entity
     * @throws IllegalStateException If the user does not exist, or if required fields are missing
     *         or do not meet validation criteria
     */
    @Transactional
    public ApplicationUser updateUser(Long userId, ApplicationUserDTO user) {
        Optional<ApplicationUser> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User with this id does not exist");
        }
        if(user.getEmail().isEmpty() || user.getPassword().isEmpty() || user.getUserName().isEmpty()) {
            throw new IllegalStateException(
                    "Required fields are missing in update operation"
            );
        }

        checkConditions(user);
        userOptional.get().setUserName(user.getUserName());
        userOptional.get().setEmail(user.getEmail());
        userOptional.get().setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(userOptional.get());
    }

    /**
     * Deletes a user from the repository by their unique identifier.
     *
     * @param id The unique identifier of the user to be deleted
     * @throws IllegalStateException if no user is found with the provided id
     */
    public void deleteUser(Long id) {
        Optional<ApplicationUser> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new IllegalStateException("User with this id does not exist");
        }
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a user by their unique identifier with caching support.
     *
     * This method first attempts to fetch the user from Redis cache. If not found in cache,
     * it queries the user repository. When a user is found in the repository, it is cached
     * in Redis for future quick access.
     *
     * @param id The unique identifier of the user to retrieve
     * @return An Optional containing the ApplicationUser if found
     * @throws IllegalStateException if no user exists with the given id
     */
    public Optional<ApplicationUser> getUserById(Long id) {
        String redisKey = RedisService.REDIS_PREFIX + id;

        // Check if the user is in Redis cache
        ApplicationUser cachedUser = redisService.get(redisKey, ApplicationUser.class);

        if (cachedUser != null) {
            // Return user from Redis cache
            return Optional.of(cachedUser);
        } else {
            Optional<ApplicationUser> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                throw new IllegalStateException("User with this id does not exist");
            }
            redisService.set(redisKey, userOptional.get(), 1);
            return userOptional;

        }
    }
    /**
     * Authenticates a user and generates a JWT token upon successful login.
     *
     * @param loginDTO Contains the user's login credentials (username and password)
     * @return A JWT token string for authenticated users
     * @throws IllegalStateException If authentication fails or credentials are invalid
     */
    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getUsername());
        }
        throw new IllegalStateException("Authentication failed");
    }

}
