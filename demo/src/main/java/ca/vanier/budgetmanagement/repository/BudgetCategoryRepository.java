//Communcating with the database about saving, finding or deleting categories
@Repository
public interface BudgetCategoryRepository extends JpaRepository<BudgetCategory, Long> {
}
