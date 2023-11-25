package zw.co.equals.accountservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.equals.accountservice.models.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
