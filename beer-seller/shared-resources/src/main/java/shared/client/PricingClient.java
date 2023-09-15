package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shared.dto.pricing.PricingCreationDto;
import shared.dto.pricing.PricingInfoResponseDto;

import java.util.List;
import java.util.Set;

@FeignClient(value = "PricingClient", url = "${url.pricing}")
public interface PricingClient {
    @GetMapping
    List<PricingInfoResponseDto> getPricing(@RequestParam final Set<Long> beerIds);

    @PostMapping
    void registerPrices(@RequestBody List<PricingCreationDto> pricingCreationDtoList);
}
