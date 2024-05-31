package dev.leonwong.service;

import dev.leonwong.model.User;
import dev.leonwong.model.UserRole;
import dev.leonwong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(username); // Here username will be the email
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        UserRole role = user.getRole();

        List<GrantedAuthority> authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority(role.toString()));


        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorityList);
    }
}
