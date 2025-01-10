package com.innovatrix.ahaar.controller;

import com.innovatrix.ahaar.DTO.JwtResponseDTO;
import com.innovatrix.ahaar.DTO.RefreshTokenRequestDTO;
import com.innovatrix.ahaar.model.APIResponse;
import com.innovatrix.ahaar.model.ApplicationUser;
import com.innovatrix.ahaar.DTO.ApplicationUserDTO;
import com.innovatrix.ahaar.DTO.LoginDTO;
import com.innovatrix.ahaar.model.RefreshToken;
import com.innovatrix.ahaar.service.JWTService;
import com.innovatrix.ahaar.service.RefreshTokenService;
import com.innovatrix.ahaar.service.UserService;
import com.innovatrix.ahaar.service.UserServiceInterface;
import com.innovatrix.ahaar.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/user")
public class UserController {

    UserServiceInterface userService;
    RefreshTokenService refreshTokenService;
    JWTService jwtService;

    /**
     * Constructs a UserController with dependencies for user management, token refresh, and JWT operations.
     *
     * @param userService The service responsible for user-related operations
     * @param refreshTokenService The service handling refresh token management
     * @param jwtService The service for generating and managing JSON Web Tokens (JWT)
     */
    @Autowired
    public UserController(UserService userService, RefreshTokenService refreshTokenService, JWTService jwtService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    /**
     * Retrieves a paginated list of users from the system.
     *
     * @param page The page number for pagination, starting from 0. Defaults to 0 if not specified.
     * @param size The number of users to return per page. Defaults to 10 if not specified.
     * @return A ResponseEntity containing an APIResponse with a Page of ApplicationUser objects
     *         - Returns 200 OK with user list if users are found
     *         - Returns 404 Not Found if no users exist in the system
     */
    @GetMapping("/all")
    public ResponseEntity<APIResponse<Page<ApplicationUser>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ApplicationUser> allUsers =  userService.getUsers(page, size);

        if(allUsers.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), "No user to retrieve", allUsers));
        }

        return ResponseEntity.ok(ResponseBuilder.success(HttpStatus.OK.value(), "User retrieved successfully", allUsers));
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve
     * @return A ResponseEntity containing an APIResponse with the user details if found,
     *         or a 404 error response if no user exists with the given ID
     * @throws IllegalArgumentException if the provided user ID is null
     */
    @GetMapping(path = "{user_id}")
    public ResponseEntity<APIResponse<Optional<ApplicationUser>>> getUserById(@PathVariable("user_id") Long id) {
        Optional<ApplicationUser> user = userService.getUserById(id);

        if(user.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ResponseBuilder.error(HttpStatus.NOT_FOUND.value(), "User not found", null));
        }

        return ResponseEntity.ok(ResponseBuilder.success(HttpStatus.OK.value(), "User retrieved successfully", user));
    }

    /**
     * Updates an existing user's information.
     *
     * @param userId The unique identifier of the user to be updated
     * @param userDTO Data transfer object containing the updated user information
     * @return ResponseEntity with an APIResponse containing the updated ApplicationUser
     * @throws UserNotFoundException if the specified user cannot be found
     * @throws ValidationException if the provided user data is invalid
     */
    @PutMapping(path = "{user_id}")
    public ResponseEntity<APIResponse<ApplicationUser>> updateUser(@PathVariable("user_id") Long userId,
                           @RequestBody ApplicationUserDTO userDTO) {

        return ResponseEntity.ok(ResponseBuilder.success(HttpStatus.OK.value(),"User updated successfully", userService.updateUser(userId, userDTO)));
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param userId The unique identifier of the user to be deleted
     * @return A ResponseEntity containing an APIResponse with a success message after user deletion
     * @throws ResourceNotFoundException if the user with the specified ID cannot be found
     */
    @DeleteMapping(path = "{user_id}")
    public ResponseEntity<APIResponse<ApplicationUserDTO>> deleteUser(@PathVariable("user_id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ResponseBuilder.success(HttpStatus.OK.value(),"User deleted successfully", null));

    }

    /**
     * Creates a new user in the system.
     *
     * @param userDTO The data transfer object containing user registration details
     * @return A ResponseEntity with an APIResponse containing the newly created user
     * @throws IllegalArgumentException if the user registration data is invalid
     */
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<Optional<ApplicationUser>>> addUser(@RequestBody ApplicationUserDTO userDTO) {
        Optional<ApplicationUser> newUser = userService.addUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.success(HttpStatus.OK.value(), "User created successfully", newUser));
    }

    /**
     * Authenticates a user and generates JWT and refresh tokens.
     *
     * @param loginDTO Contains user credentials for authentication
     * @return ResponseEntity with JWT and refresh tokens upon successful login
     *
     * @throws AuthenticationException if login credentials are invalid
     *
     * @see LoginDTO
     * @see JwtResponseDTO
     */
    @PostMapping("/login")
    public ResponseEntity<APIResponse<JwtResponseDTO>> authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        String jwtToken = userService.login(loginDTO);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginDTO.getUsername());
        JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                .accessToken(jwtToken)
                .token(refreshToken.getToken()).build();

        return ResponseEntity.status(HttpStatus.OK).body(ResponseBuilder.success(HttpStatus.OK.value(), "logged in successfully", jwtResponseDTO))    ;
    }

    /**
     * Generates a new JWT access token using a provided refresh token.
     *
     * @param refreshTokenRequest The request containing the refresh token to be validated
     * @return A ResponseEntity containing a JWT response with a new access token
     * @throws RuntimeException if the provided refresh token is not found in the database
     *
     * @see RefreshTokenRequestDTO
     * @see JwtResponseDTO
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<APIResponse<JwtResponseDTO>> getRefreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.toDTO().getUserName());
                    JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                    return ResponseEntity.status(HttpStatus.OK).body(ResponseBuilder.success(HttpStatus.OK.value(), "JWT token generated successfully", jwtResponseDTO));
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }
}
