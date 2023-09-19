package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shared.dto.order.creation.OrderCreationDto;

@FeignClient(value = "OrderClient", url = "${url.resources-relay}")
@RequestMapping("/orders")
public interface OrderClient {
    @PostMapping
    void createOrder(@RequestBody final OrderCreationDto orderCreationDto);
}
