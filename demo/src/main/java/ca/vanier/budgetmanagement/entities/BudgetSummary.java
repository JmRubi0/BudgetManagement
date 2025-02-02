package ca.vanier.budgetmanagement.entities;

public class BudgetSummary {
    private double totalIncome;
    private double totalExpenses;
    private double balance;
    public BudgetSummary(double totalIncome, double totalExpenses, double balance) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = balance;
    }
    // Getters
    public double getTotalIncome() { return totalIncome; }
    public double getTotalExpenses() { return totalExpenses; }
    public double getBalance() { return balance; }
}
