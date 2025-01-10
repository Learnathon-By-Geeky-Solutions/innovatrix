package com.innovatrix.ahaar.service;

import com.innovatrix.ahaar.model.ApplicationUser;
import com.innovatrix.ahaar.model.ApplicationUserDetails;
import com.innovatrix.ahaar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details by username for Spring Security authentication.
     *
     * @param username the username to search for in the user repository
     * @return UserDetails containing the authenticated user's information
     * @throws UsernameNotFoundException if no user is found with the given username
     *
     * This method retrieves an ApplicationUser from the repository based on the provided username.
     * If the user is not found, it logs the username and throws a UsernameNotFoundException.
     * If the user is found, it creates and returns an ApplicationUserDetails object.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            System.out.println(username + " not found");
            throw new UsernameNotFoundException(username + " not found");
        }
        System.out.println(user.toString());
        return new ApplicationUserDetails(user.get());
    }
}
