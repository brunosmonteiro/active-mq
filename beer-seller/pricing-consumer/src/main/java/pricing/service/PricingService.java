package pricing.service;

import org.springframework.stereotype.Service;
import pricing.producer.PricingValidatedProducer;
import shared.client.PricingClient;
import shared.constants.pricing.PricingStatus;
import shared.dto.pricing.calculation.PricingCalculationRequestBeerDto;
import shared.dto.pricing.calculation.PricingCalculationRequestDto;
import shared.dto.pricing.calculation.PricingCalculationResponseBeerDto;
import shared.dto.pricing.calculation.PricingCalculationResponseDto;
import shared.dto.pricing.error.PricingErrorDetailDto;
import shared.dto.pricing.error.PricingErrorDto;
import shared.dto.pricing.PricingInfoResponseDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public record PricingService(
        PricingValidatedProducer pricingValidatedProducer,
        PricingClient pricingClient) {

    public void validatePricing(final PricingCalculationRequestDto request) {
        final List<PricingInfoResponseDto> pricing = pricingClient.getPricing(extractBeerIds(request));
        final Map<Long, PricingInfoResponseDto> pricingMap = mapIdsToPricingInfoResponseDto(pricing);
        pricingValidatedProducer.sendMessage(
            new PricingCalculationResponseDto(
                request.getOrderId(),
                getTotalPrice(request.getBeers(), pricingMap),
                getBeerCalculation(request, pricingMap)
            )
        );
    }

    private Set<Long> extractBeerIds(final PricingCalculationRequestDto request) {
        return request.getBeers().stream()
            .map(PricingCalculationRequestBeerDto::getBeerId)
            .collect(Collectors.toSet());
    }

    private Map<Long, PricingInfoResponseDto> mapIdsToPricingInfoResponseDto(
            final List<PricingInfoResponseDto> beers) {
        return beers.stream()
            .collect(Collectors.toMap(PricingInfoResponseDto::getBeerId, Function.identity()));
    }

    private BigDecimal getTotalPrice(
            final List<PricingCalculationRequestBeerDto> beers,
            final Map<Long, PricingInfoResponseDto> pricingMap) {
        return beers.stream().map(beer ->
            ofNullable(pricingMap.get(beer.getBeerId()))
                .map(PricingInfoResponseDto::getUnitPrice)
                .orElse(BigDecimal.ZERO)
                .multiply(BigDecimal.valueOf(beer.getQuantity())))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
    }

    private List<PricingCalculationResponseBeerDto> getBeerCalculation(
            final PricingCalculationRequestDto request,
            final Map<Long, PricingInfoResponseDto> pricingMap) {
        return request.getBeers().stream().map(beer -> {
            final var pricingBeer = pricingMap.get(beer.getBeerId());
            PricingStatus status = PricingStatus.VALID;
            if (pricingBeer == null) {
                status = PricingStatus.INVALID;
            }
            return new PricingCalculationResponseBeerDto(
                beer.getBeerId(),
                beer.getQuantity(),
                status,
                ofNullable(pricingBeer).map(PricingInfoResponseDto::getUnitPrice).orElse(null));
        })
        .toList();
    }
}
