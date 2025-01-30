package ca.vanier.budgetmanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.vanier.budgetmanagement.entities.Transaction;


//Communcating with the database to save or lookin up for transactions. 
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}