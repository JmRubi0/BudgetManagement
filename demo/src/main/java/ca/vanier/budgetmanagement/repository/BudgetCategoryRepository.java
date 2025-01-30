package ca.vanier.budgetmanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.vanier.budgetmanagement.entities.BudgetCategory;


//Communcating with the database about saving, finding or deleting categories
@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
}
