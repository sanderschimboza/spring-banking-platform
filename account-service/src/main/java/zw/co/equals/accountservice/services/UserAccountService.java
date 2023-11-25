package zw.co.equals.accountservice.services;

import zw.co.equals.accountservice.dto.UserAccountDto;
import zw.co.equals.accountservice.models.UserAccount;

import java.util.List;

public interface UserAccountService {
    String createUserAccount(UserAccountDto userAccountDto);
    UserAccount modifyUserAccountDetails(UserAccountDto userAccountDto);
    void deleteUserAccount(String accountNumber);
    UserAccount findUserAccount(String accountNumber);
    List<UserAccount> findUserAccounts();
}
