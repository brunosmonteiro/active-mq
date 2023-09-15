package order.config;

import order.service.OrderAggregatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import shared.dto.order.orchestration.OrderOrchestrationPartDto;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MessageOrchestrationConfig {
    @Bean
    public MessageChannel aggregatorChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow orderAggregationFlow(final OrderAggregatorService orderAggregatorService) {
        return IntegrationFlow.from("aggregatorChannel")
            .aggregate(aggregatorSpec ->
                aggregatorSpec
                    .correlationExpression("payload.orderId")
                    .releaseExpression("size() == 2")
                    .groupTimeout(10000)
                    .expireGroupsUponCompletion(true)
                    .outputProcessor(group -> {
                        final List<OrderOrchestrationPartDto> orderParts = group.getMessages()
                            .stream()
                            .map(message -> (OrderOrchestrationPartDto) message.getPayload())
                            .collect(Collectors.toList());
                        return orderAggregatorService.aggregate(orderParts);
                    }))
            .handle("orderService", "createOrder").get();
    }
}
