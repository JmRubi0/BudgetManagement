package ca.vanier.budgetmanagement.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

//BudgetCategory Table
@Getter
@Setter
@Entity
public class BudgetCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    //Table relations
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();
}

