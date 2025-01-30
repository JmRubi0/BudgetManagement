package ca.vanier.budgetmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.vanier.budgetmanagement.entities.User;
import ca.vanier.budgetmanagement.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
 
    public User registerUser(User user) {
        return userRepository.save(user);
    }
 
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
