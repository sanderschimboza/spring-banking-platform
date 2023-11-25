package zw.co.equals.transactionservice.exceptions.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import zw.co.equals.transactionservice.exceptions.TransactionServiceCustomException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {

        int responseCode = HttpStatus.valueOf(response.status()).value();
        log.info("Printing responseCode:::: {}", responseCode);
        log.info("Printing body:::: {}", response.body());
        log.info("Printing reason:::: {}", response.reason());



//        return new TransactionServiceCustomException(
//                response.body().toString(), HttpStatus.valueOf(response.status())
//        );
       return switch (responseCode) {

            case 500 -> new TransactionServiceCustomException(
                    "An internal server occurred while trying to communicate with remote server!",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
            case 401 -> new TransactionServiceCustomException(
                    "Unauthorized Request!",
                    HttpStatus.UNAUTHORIZED
            );
            case 404 -> new TransactionServiceCustomException(
                    "Requested resource was not found from remote server!",
                    HttpStatus.NOT_FOUND
            );
            case 400 -> new TransactionServiceCustomException(
                    "Bad Request Error Returned!",
                    HttpStatus.BAD_REQUEST
            );
            default -> new TransactionServiceCustomException(
                    "An exception was returned from remote server!",
                    HttpStatus.valueOf(response.status())
            );
        };

    }
}

