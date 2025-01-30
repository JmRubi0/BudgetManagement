package ca.vanier.budgetmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.vanier.budgetmanagement.entities.BudgetCategory;
import ca.vanier.budgetmanagement.entities.Transaction;
import ca.vanier.budgetmanagement.repository.BudgetCategoryRepository;
import ca.vanier.budgetmanagement.repository.TransactionRepository;


//Create and save budget categories and transactions
//When the method is called, it adds them to the database
@Service
public class BudgetService {
    @Autowired
    private BudgetCategoryRepository categoryRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public BudgetCategory createCategory(String name) {
        BudgetCategory category = new BudgetCategory();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
