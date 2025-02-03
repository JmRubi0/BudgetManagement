package ca.vanier.budgetmanagement.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 
     * @param year
     * @return Report filtering by group income and expenses from the year entered
     */
    public Map<String, Object> getYearlyTransactionsReport(int year) {
        Map<String, List<Transaction>> groupedTransactions = getTransactionsByYearGroupedByTypeAndDate(year);

        List<Map<String, Object>> income = groupedTransactions.get("income").stream()
                .collect(Collectors.groupingBy(t -> t.getDate().toLocalDate()))  // Group by date
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> dateIncome = new HashMap<>();
                    dateIncome.put("date", entry.getKey().toString());
                    dateIncome.put("amount", entry.getValue().stream().mapToDouble(Transaction::getAmount).sum());
                    return dateIncome;
                })
                .collect(Collectors.toList());

        List<Map<String, Object>> expenses = groupedTransactions.get("expense").stream()
                .collect(Collectors.groupingBy(t -> t.getDate().toLocalDate()))  // Group by date
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> dateExpense = new HashMap<>();
                    dateExpense.put("date", entry.getKey().toString());
                    dateExpense.put("amount", entry.getValue().stream().mapToDouble(Transaction::getAmount).sum());
                    return dateExpense;
                })
                .collect(Collectors.toList());

        Map<String, Object> report = new HashMap<>();
        report.put("income", income);
        report.put("expenses", expenses);

        return report;
    }
    

    /**
     * Create transaction
     * @param transaction
     * @return returns the save created transaction the user inputed
     */
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Reports filtering all expenses and income from entered Year
     */
    public List<Transaction> getTransactionsByYear(int year) {
        return transactionRepository.findAll().stream()
            .filter(t -> t.getDate().getYear() == year)
            .collect(Collectors.toList());
    }

}
