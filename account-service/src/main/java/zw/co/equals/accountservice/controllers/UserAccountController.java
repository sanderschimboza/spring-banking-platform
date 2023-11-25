package zw.co.equals.accountservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.equals.accountservice.dto.UserAccountDto;
import zw.co.equals.accountservice.models.UserAccount;
import zw.co.equals.accountservice.services.UserAccountService;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody UserAccountDto userAccountDto) {
        String accountNumber = userAccountService.createUserAccount(userAccountDto);
        return new ResponseEntity<>(accountNumber, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserAccount> updateUserAccount(@RequestBody UserAccountDto userAccountDto) {
        UserAccount userAccount = userAccountService.modifyUserAccountDetails(userAccountDto);
        return new ResponseEntity<>(userAccount, HttpStatus.OK);
    }

    @GetMapping("/find/{account}")
    public ResponseEntity<UserAccount> findUserAccount(@PathVariable String account) {
        UserAccount userAccount = userAccountService.findUserAccount(account);
        return new ResponseEntity<>(userAccount, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserAccount>> findAllAccounts() {
        List<UserAccount> userAccounts = userAccountService.findUserAccounts();
        return new ResponseEntity<>(userAccounts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{account}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String account) {
        userAccountService.deleteUserAccount(account);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
