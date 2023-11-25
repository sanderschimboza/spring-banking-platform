package zw.co.equals.transactionservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.equals.transactionservice.dto.FundsTransferDto;
import zw.co.equals.transactionservice.models.Transaction;
import zw.co.equals.transactionservice.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit/{account}/{amount}")
    public ResponseEntity<String> depositFunds(@PathVariable String account, @PathVariable int amount) {
        Double bal = transactionService.depositFunds(account, amount);
        return new ResponseEntity<>("Deposit successful. New account balance " +
                "is $" + bal, HttpStatus.OK);
    }

    @PostMapping("/withdraw/{account}/{amount}")
    public ResponseEntity<String> withdrawFunds(@PathVariable String account, @PathVariable int amount) {
        String transactionRef = transactionService.withdrawFunds(account, amount);
        return new ResponseEntity<>(transactionRef, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@Valid @RequestBody FundsTransferDto fundsTransferDto) {
        String transactionRef = transactionService.transferFunds(fundsTransferDto);
        return new ResponseEntity<>(transactionRef, HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> balanceEnquiry(@RequestParam String account) {
        Double bal = transactionService.getAccountBalance(account);
        return new ResponseEntity<>(bal, HttpStatus.OK);
    }

    @GetMapping("/findByAccount/{account}")
    public ResponseEntity<List<Transaction>> findUserTransactions(@PathVariable String account) {
        List<Transaction> transactions = transactionService.findUserTransactions(account);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/statement/{account}")
    public ResponseEntity<List<Transaction>> generateAccountStatement(@PathVariable String account,
                                                                      @RequestParam(required = false) String startDate,
                                                                      @RequestParam(required = false) String endDate)  {

        List<Transaction> transactionList = transactionService.generateAccountStatement(account,startDate,endDate);
        return new ResponseEntity<>(transactionList,HttpStatus.OK);
    }
}
