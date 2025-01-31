package ca.vanier.budgetmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ca.vanier.budgetmanagement.entities.BudgetCategory;
import ca.vanier.budgetmanagement.entities.Transaction;
import ca.vanier.budgetmanagement.service.BudgetService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController

@RequestMapping("/budget")

public class BudgetController {

    @Autowired

    private BudgetService budgetService;
 
    @PostMapping("/category")

    public ResponseEntity<BudgetCategory> createCategory(@RequestBody String categoryName) {

        BudgetCategory category = budgetService.createCategory(categoryName);

        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }
 
    @PostMapping("/transaction")

    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {

        Transaction createdTransaction = budgetService.createTransaction(transaction);

        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);

    }

}

 