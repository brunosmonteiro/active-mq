package displayer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.client.OrderClient;

@RestController
@RequestMapping("/orders")
public record OrderController(OrderClient orderClient) {

}
