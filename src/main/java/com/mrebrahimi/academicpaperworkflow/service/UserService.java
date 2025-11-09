package com.mrebrahimi.academicpaperworkflow.service;

import com.mrebrahimi.academicpaperworkflow.domain.Role;
import com.mrebrahimi.academicpaperworkflow.domain.User;
import com.mrebrahimi.academicpaperworkflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String username, String fullName, Role role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        User user = new User(username, fullName, role);
        return userRepository.save(user);
    }
}

