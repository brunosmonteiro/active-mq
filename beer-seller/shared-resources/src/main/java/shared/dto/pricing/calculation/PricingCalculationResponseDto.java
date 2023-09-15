package shared.dto.pricing.calculation;

import java.math.BigDecimal;
import java.util.List;

public record PricingCalculationResponseDto(
    Long orderId, BigDecimal totalPrice, List<PricingCalculationResponseBeerDto> beers) {
}
