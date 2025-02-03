package ca.vanier.budgetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.vanier.budgetmanagement.entities.BudgetCategory;
import ca.vanier.budgetmanagement.entities.BudgetSummary;
import ca.vanier.budgetmanagement.entities.Transaction;

import ca.vanier.budgetmanagement.repository.BudgetCategoryRepository;
import ca.vanier.budgetmanagement.repository.TransactionRepository;
import jakarta.transaction.Transactional;


/**
 * Method logic for Budgetcategories
 */
@Service
@Transactional
public class BudgetService {
    @Autowired
    private BudgetCategoryRepository budgetCategoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /*
     * BUDGET CRUD
     */
    //Create budget
    public BudgetCategory createCategory(BudgetCategory category) {
        return budgetCategoryRepository.save(category);
    }
    //Updates Budget by its id
    public BudgetCategory updateCategory(Long id, BudgetCategory updatedCategory) {
        return budgetCategoryRepository.findById(id).map(category -> {
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());
            return budgetCategoryRepository.save(category);
        }).orElseThrow(() -> new RuntimeException("Category not found"));
    }
    //Deletes Budget by its id
    public void deleteCategory(Long id) {
        BudgetCategory budgetCategory = budgetCategoryRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
 
            budgetCategoryRepository.delete(budgetCategory);
    }

    /**
     * 
     * @return Takes all income and expense to sum up and shows remain balance
     */
    public BudgetSummary getBudgetSummary() {
        double totalIncome = transactionRepository.findAll().stream()
            .filter(t -> "income".equalsIgnoreCase(t.getType()))
            .mapToDouble(Transaction::getAmount)
            .sum();
     
        double totalExpenses = transactionRepository.findAll().stream()
            .filter(t -> "expense".equalsIgnoreCase(t.getType()))
            .mapToDouble(Transaction::getAmount)
            .sum();
     
        return new BudgetSummary(totalIncome, totalExpenses, totalIncome - totalExpenses);
    }

    public List<BudgetCategory> getCategoriesByName(String name) {
        if (name != null && !name.isEmpty()) {
            return budgetCategoryRepository.findByNameContainingIgnoreCase(name); // Case-insensitive search
        }
        return budgetCategoryRepository.findAll(); // If no name is provided, return all categories
    }


    /**
     * Create transaction
     * @param transaction
     * @return returns the save created transaction the user inputed
     */
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

}
