package ca.vanier.budgetmanagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ca.vanier.budgetmanagement.entities.BudgetCategory;
import ca.vanier.budgetmanagement.entities.BudgetSummary;
import ca.vanier.budgetmanagement.entities.Transaction;
import ca.vanier.budgetmanagement.repository.TransactionRepository;
import ca.vanier.budgetmanagement.service.BudgetService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Budget category controller where users can create update and delete their categories. A list of all budgets and summary are also available
 */
@RestController
@RequestMapping("/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private TransactionRepository transactionRepository;
    
    //Creates transactional expense or income
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = budgetService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);

    }

    /**
     * 
     * @param year
     * @return Report filtering in 2 seperate groups of showing all incomes and expense in the filtered year
     */
    public Map<String, List<Transaction>> getTransactionsByYearGroupedByTypeAndDate(int year) {
        // Get all transactions for the given year
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(t -> t.getDate().getYear() == year) // Filter by year
                .collect(Collectors.toList());

        // Group transactions by type (income or expense) and date
        Map<String, List<Transaction>> groupedByType = new HashMap<>();
        groupedByType.put("income", new ArrayList<>());
        groupedByType.put("expense", new ArrayList<>());

        for (Transaction transaction : transactions) {
            if ("income".equalsIgnoreCase(transaction.getType())) {
                groupedByType.get("income").add(transaction);
            } else if ("expense".equalsIgnoreCase(transaction.getType())) {
                groupedByType.get("expense").add(transaction);
            }
        }

        return groupedByType;
    }

    //Reprot income vs expenses
    @GetMapping("/transactions/report/{year}")
    public ResponseEntity<Map<String, Object>> getYearlyTransactionsReport(@PathVariable int year) {
        Map<String, Object> report = budgetService.getYearlyTransactionsReport(year);
        return ResponseEntity.ok(report);
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

    //Filtering by bduget category name
    @GetMapping("/category")
    public ResponseEntity<List<BudgetCategory>> getCategoriesByName(@RequestParam(required = false) String name) {
        List<BudgetCategory> categories = budgetService.getCategoriesByName(name);
        return ResponseEntity.ok(categories);
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

 