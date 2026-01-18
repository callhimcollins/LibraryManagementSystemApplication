package com.project.LibraryManagementSystemApplication.service.impl;

import com.project.LibraryManagementSystemApplication.model.User;
import com.project.LibraryManagementSystemApplication.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("User does not exist with username: " + username);
        }

        GrantedAuthority authority=new SimpleGrantedAuthority(user.getRole().toString());

        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(authority);


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );

    }
}
