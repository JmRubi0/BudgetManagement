package ca.vanier.budgetmanagement.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

//Transaction Table
@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;
    private LocalDateTime date;
    private String type;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")  // Foreign key to users table
    private User owner;  // This is what User.transactions maps to
    
    @ManyToOne
    @JoinColumn(name = "category_id")  // Foreign key to budget_category table
    private BudgetCategory category;
    
}
