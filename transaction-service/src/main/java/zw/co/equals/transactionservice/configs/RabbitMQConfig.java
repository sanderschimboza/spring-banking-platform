package zw.co.equals.transactionservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.equals.transactionservice.utils.Constants;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue transactionAlertQueue() {
        return new Queue(Constants.TRANSACTION_ALERT_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
