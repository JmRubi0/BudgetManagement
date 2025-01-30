//Communcating with the database to save or lookin up for transactions. 
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}