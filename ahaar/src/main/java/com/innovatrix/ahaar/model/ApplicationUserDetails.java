package com.innovatrix.ahaar.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ApplicationUserDetails implements UserDetails {

    private final ApplicationUser user;

    /**
     * Constructs an ApplicationUserDetails instance with the specified ApplicationUser.
     *
     * @param applicationUser the ApplicationUser object representing the user details
     *                        for authentication and authorization purposes
     */
    public ApplicationUserDetails(ApplicationUser applicationUser) {
        this.user = applicationUser;
    }
    /**
     * Returns the granted authorities for the user.
     *
     * @return An empty list of granted authorities, indicating no specific permissions are assigned to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Retrieves the password for the current application user.
     *
     * @return the user's password as a String
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Retrieves the username of the application user.
     *
     * @return the username associated with the ApplicationUser instance
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * Determines whether the user's account is non-expired.
     *
     * @return always returns {@code true}, indicating the account is active and not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Determines whether the user's account is non-locked.
     *
     * @return always returns {@code true}, indicating the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have not expired.
     *
     * @return always returns {@code true}, indicating that the user's credentials are valid and not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Determines whether the user account is enabled.
     *
     * @return always returns {@code true}, indicating the user account is active and can be used for authentication
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
