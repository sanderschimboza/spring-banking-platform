package zw.co.equals.cloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.equals.cloudgateway.utils.Constants;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/transaction/**")
                        .filters(f -> f
                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
                                        .setFallbackUri("forward:/" + Constants.FALLBACK_TRANSACTION_URI)
                                )
                        )
                        .uri("lb://TRANSACTION-SERVICE")
                )

                .route(r -> r.path("/account/**")
                        .filters(f -> f
                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
                                        .setFallbackUri("forward:/" + Constants.FALLBACK_ACCOUNT_URI)
                                )
                        )
                        .uri("lb://ACCOUNT-SERVICE")
                )

                .route(r -> r.path("/support/**")
                        .filters(f -> f
                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
                                        .setFallbackUri("forward:/" + Constants.FALLBACK_SUPPORT_URI)
                                )
                        )
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .route(r -> r.path("/supportAdmin/**")
                        .filters(f -> f
                                .circuitBreaker(h -> h.setName(Constants.CB_IDENTITY)
                                        .setFallbackUri("forward:/" + Constants.FALLBACK_SUPPORT_URI)
                                )
                        )
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .build();
    }
}
