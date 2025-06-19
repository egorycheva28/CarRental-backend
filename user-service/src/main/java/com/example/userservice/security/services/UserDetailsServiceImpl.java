package com.example.userservice.security.services;

import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        // User user = userRepository.findByUsername(username)
        //        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        return UserDetailsImpl.build(user);
    }

}
