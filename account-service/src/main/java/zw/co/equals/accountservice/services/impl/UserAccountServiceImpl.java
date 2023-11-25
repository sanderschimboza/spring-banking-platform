package zw.co.equals.accountservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import zw.co.equals.accountservice.dto.UserAccountDto;
import zw.co.equals.accountservice.exceptions.UserAccountCustomException;
import zw.co.equals.accountservice.models.UserAccount;
import zw.co.equals.accountservice.repositories.UserAccountRepository;
import zw.co.equals.accountservice.services.UserAccountService;
import zw.co.equals.accountservice.utils.Validator;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public String createUserAccount(UserAccountDto userAccountDto) {
        log.info("Going to create new account with following details -> {}", userAccountDto);

        validateAccount(userAccountDto.getAccountNumber());

        UserAccount userAccount = UserAccount.builder()
                .accountNumber(userAccountDto.getAccountNumber())
                .accountName(userAccountDto.getAccountName())
                .accountDetails(userAccountDto.getAccountDetails())
                .accountBal(0)
                .accountType(userAccountDto.getAccountType())
                .build();

        return userAccountRepository.save(userAccount)
                .getAccountNumber();
    }

    @Override
    public UserAccount modifyUserAccountDetails(UserAccountDto userAccountDto) {
        log.info("Going to modify account with following details -> {}", userAccountDto);

        UserAccount userAccount = findUserAccount(userAccountDto.getAccountNumber());
        userAccount.setAccountBal(userAccountDto.getAccountBal());
        userAccount.setAccountDetails(userAccountDto.getAccountDetails());
        userAccount.setAccountType(userAccountDto.getAccountType());

        return userAccountRepository.save(userAccount);
    }

    @Override
    @CacheEvict(value = "UserAccount", key = "#accountNumber")
    public void deleteUserAccount(String accountNumber) {
        log.info("Going to delete account with account number -> {}", accountNumber);
        userAccountRepository.deleteById(accountNumber);
    }

    @Override
    public UserAccount findUserAccount(String accountNumber) {
        log.info("finding account with account number -> {}", accountNumber);

        Optional<UserAccount> userAccount = userAccountRepository.findById(accountNumber);

        if (userAccount.isEmpty()) {
            throw new UserAccountCustomException("User Account with account " + accountNumber
                    + " not found!",
                    HttpStatus.NOT_FOUND
            );
        }
        return userAccount.get();
    }

    @Override
    @Cacheable(value = "UserAccount")
    public List<UserAccount> findUserAccounts() {
        log.info("Listing all accounts now");
        return userAccountRepository.findAll();
    }

    private void validateAccount(String account) {
        if (!Validator.validateAccount(account)) {
            throw new UserAccountCustomException(
                    "Invalid Account Number Format please try again!",
                    HttpStatus.BAD_REQUEST
            );
        }

        log.info("Account Number Validated => {}", account);
        Optional<UserAccount> userAccount = this.userAccountRepository.findById(account);
        if (userAccount.isPresent()) {
            throw new UserAccountCustomException(
                    "User Account Is Already Present",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
