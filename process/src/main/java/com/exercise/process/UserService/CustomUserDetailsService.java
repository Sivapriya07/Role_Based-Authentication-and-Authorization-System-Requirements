package com.exercise.process.UserService;

import com.exercise.process.Entity.User;
import com.exercise.process.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (user.getRole() !=null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" +user.getRole().getRolename()));
        }else{
            throw new UsernameNotFoundException("user role not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

        }
}
