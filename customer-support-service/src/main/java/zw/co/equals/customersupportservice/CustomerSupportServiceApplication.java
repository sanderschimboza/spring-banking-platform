package zw.co.equals.customersupportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CustomerSupportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerSupportServiceApplication.class, args);
    }

}
