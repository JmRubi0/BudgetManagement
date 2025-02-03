package ca.vanier.budgetmanagement.demo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.vanier.budgetmanagement.entities.BudgetCategory;
import ca.vanier.budgetmanagement.repository.BudgetCategoryRepository;
import ca.vanier.budgetmanagement.service.BudgetService;

public class BudgetServiceTest {

    @Mock
    private BudgetCategoryRepository budgetCategoryRepository;

    @InjectMocks
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        BudgetCategory category = new BudgetCategory();
        category.setName("Food");
        
        when(budgetCategoryRepository.save(any(BudgetCategory.class))).thenReturn(category);
        
        BudgetCategory savedCategory = budgetService.createCategory(category);
        assertNotNull(savedCategory);
        assertEquals("Food", savedCategory.getName());
    }

    @Test
    void testUpdateCategory() {
        BudgetCategory existingCategory = new BudgetCategory();
        existingCategory.setId(1L);
        existingCategory.setName("Old Name");
        
        BudgetCategory updatedCategory = new BudgetCategory();
        updatedCategory.setName("New Name");
        
        when(budgetCategoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(budgetCategoryRepository.save(any(BudgetCategory.class))).thenReturn(updatedCategory);
        
        BudgetCategory result = budgetService.updateCategory(1L, updatedCategory);
        assertEquals("New Name", result.getName());
    }

    @Test
    void testDeleteCategory() {
        BudgetCategory category = new BudgetCategory();
        category.setId(1L);
        
        when(budgetCategoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(budgetCategoryRepository).delete(category);
        
        assertDoesNotThrow(() -> budgetService.deleteCategory(1L));
    }
}