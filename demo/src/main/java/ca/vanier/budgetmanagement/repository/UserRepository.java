package ca.vanier.budgetmanagement.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.vanier.budgetmanagement.entities.User;


//Looking for a user by their username in the database
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
