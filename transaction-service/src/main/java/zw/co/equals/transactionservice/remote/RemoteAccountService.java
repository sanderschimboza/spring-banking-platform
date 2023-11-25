package zw.co.equals.transactionservice.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zw.co.equals.transactionservice.dto.UserAccountDto;

@FeignClient(name = "ACCOUNT-SERVICE")
public interface RemoteAccountService {

    @GetMapping("account/find/{account}")
    UserAccountDto getUserAccount(@PathVariable(value = "account") String account);

    @PutMapping("account/update")
    UserAccountDto updateUserAccount(@RequestBody UserAccountDto userAccountDto);
}


