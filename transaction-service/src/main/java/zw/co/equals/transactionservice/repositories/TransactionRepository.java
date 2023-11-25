package zw.co.equals.transactionservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zw.co.equals.transactionservice.models.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findTransactionByTransactionRef(String ref);
    List<Transaction> findTop5ByFromAccountOrToAccountOrderByIdDesc(String fromAccount, String toAccount);
    List<Transaction> findTransactionByFromAccountOrToAccount(String fromAccount, String toAccount);

    @Query(value = "SELECT * FROM transactions " +
                   "WHERE (from_account = :fromAccount " +
                   "OR to_account = :toAccount) " +
                   "AND transaction_date >= :startDate " +
                   "AND transaction_date <= :endDate", nativeQuery = true)
    List<Transaction> findUserTransactionBetweenDates(String fromAccount, String toAccount, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT * FROM transactions " +
                   "WHERE (from_account = :fromAccount " +
                   "OR to_account = :toAccount) " +
                   "AND transaction_date >= :startDate", nativeQuery = true)
    List<Transaction> findUserTransactionsFromDate(String fromAccount, String toAccount, LocalDate startDate);

    @Query(value = "SELECT * FROM transactions " +
                   "WHERE (from_account = :fromAccount " +
                   "OR to_account = :toAccount) " +
                   "AND transaction_date <= :endDate", nativeQuery = true)
    List<Transaction> findUserTransactionsToDate(String fromAccount, String toAccount, LocalDate endDate);

}
