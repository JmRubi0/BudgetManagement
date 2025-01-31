package ca.vanier.budgetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.vanier.budgetmanagement.entities.User;
import ca.vanier.budgetmanagement.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
 
    //For all users to register, user role is set to default
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().isEmpty()){
            user.setRole("USER");
        }
        return userRepository.save(user);
    }
 
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    
    //ADMIN functionalities
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
 
    public User updateUserRole(String username, String newRole) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRole(newRole.toUpperCase());
        return userRepository.save(user);
    }
 
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
 
        userRepository.delete(user);
    }

}
