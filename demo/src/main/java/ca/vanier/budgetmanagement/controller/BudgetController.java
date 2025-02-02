package ca.vanier.budgetmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ca.vanier.budgetmanagement.entities.BudgetCategory;
import ca.vanier.budgetmanagement.entities.BudgetSummary;
import ca.vanier.budgetmanagement.entities.Transaction;
import ca.vanier.budgetmanagement.service.BudgetService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Budget category controller where users can create update and delete their categories. A list of all budgets and summary are also available
 */
@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;
    
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = budgetService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);

    }
    
    //Creates budgets category
    @PostMapping("/category")
        public ResponseEntity<BudgetCategory> createCategory(@RequestBody BudgetCategory category) {
        BudgetCategory savedCategory = budgetService.createCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    //Edit Budget Category
    @PutMapping("/category/{id}")
    public ResponseEntity<BudgetCategory> updateCategory(@PathVariable Long id, @RequestBody BudgetCategory updatedCategory) {
        BudgetCategory category = budgetService.updateCategory(id, updatedCategory);
        return ResponseEntity.ok(category);
    }

    //Delete Budget Category
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        budgetService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }

    /**
     * Loads the budget summaries
     */
    @GetMapping("/summary")
        public ResponseEntity<BudgetSummary> getBudgetSummary() {
            BudgetSummary summary = budgetService.getBudgetSummary();
        return ResponseEntity.ok(summary);
    }
    
}

 