package zw.co.equals.cloudgateway.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.equals.cloudgateway.utils.Constants;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @RequestMapping("/transaction")
    public ResponseEntity<String> transactionFallBack() {
        return new ResponseEntity<>("Transaction "
                + Constants.SERVICE_UNAVAILABLE_MSG, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/account")
    public ResponseEntity<String> accountFallBack() {
        return new ResponseEntity<>("Account "
                + Constants.SERVICE_UNAVAILABLE_MSG, HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/support")
    public ResponseEntity<String> customerSupportFallBack() {
        return new ResponseEntity<>("Customer Support "
                + Constants.SERVICE_UNAVAILABLE_MSG, HttpStatus.NOT_FOUND);
    }
}
