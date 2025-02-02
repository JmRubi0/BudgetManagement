package ca.vanier.budgetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.vanier.budgetmanagement.entities.User;
import ca.vanier.budgetmanagement.service.UserService;


/*
 * Admin can over see all user activity which he can promote/demote a user and delete a user
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
 
    @Autowired
    private UserService userService;
    
    //Admin as control of all users
    // View all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
 
    // Update user role
    @PutMapping("/users/{username}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable String username, @RequestParam String role) {
        User updatedUser = userService.updateUserRole(username, role);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
 
    // Delete user
    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
 
}