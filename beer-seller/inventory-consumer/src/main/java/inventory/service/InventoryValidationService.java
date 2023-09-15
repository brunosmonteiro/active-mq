package inventory.service;

import inventory.producer.InventoryValidatedProducer;
import org.springframework.stereotype.Service;
import shared.client.InventoryClient;
import shared.constants.inventory.InventoryValidationStatus;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.validation.InventoryValidationRequestDetailDto;
import shared.dto.inventory.validation.InventoryValidationRequestDto;
import shared.dto.inventory.validation.InventoryValidationResponseDetailDto;
import shared.dto.inventory.validation.InventoryValidationResponseDto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public record InventoryValidationService(
        InventoryValidatedProducer inventoryValidatedProducer,
        InventoryClient inventoryClient) {

    public void validateStock(final InventoryValidationRequestDto request) {
        final Set<Long> beerIds = request.getBeers().stream()
            .map(InventoryValidationRequestDetailDto::getBeerId).collect(Collectors.toSet());
        final var inventory = inventoryClient.getInventoryBeersById(beerIds);
        final var validation = getValidation(request, inventory);
        inventoryValidatedProducer.sendMessage(validation);
    }

    private InventoryValidationResponseDto getValidation(
            final InventoryValidationRequestDto request,
            final List<InventoryBeerDto> inventoryBeers) {
        final Map<Long, InventoryValidationRequestDetailDto> requestMap = buildRequestMap(request.getBeers());
        final Map<Long, Integer> beerInventoryMap = buildBeerInventoryMap(inventoryBeers);
        return new InventoryValidationResponseDto(
            request.getOrderId(), validateInventory(requestMap, beerInventoryMap));
    }

    private List<InventoryValidationResponseDetailDto> validateInventory(
            final Map<Long, InventoryValidationRequestDetailDto> requestMap,
            final Map<Long, Integer> beerInventoryMap) {
        return requestMap.values()
            .stream()
            .map(requestBeer -> buildInventoryValidatedDto(requestBeer, beerInventoryMap))
            .toList();
    }

    private InventoryValidationResponseDetailDto buildInventoryValidatedDto(
            final InventoryValidationRequestDetailDto requestBeer,
            final Map<Long, Integer> beerInventoryMap) {
        final var validationDetailResponse = new InventoryValidationResponseDetailDto();
        final var beerQuantity = ofNullable(beerInventoryMap.get(requestBeer.getBeerId())).orElse(0);
        InventoryValidationStatus status;
        if (beerQuantity == 0) {
            status = InventoryValidationStatus.COMPLETELY_MISSING;
        } else if (beerQuantity < requestBeer.getQuantity()) {
            status = InventoryValidationStatus.PARTIALLY_MISSING;
        } else {
            status = InventoryValidationStatus.VALID;
        }
        validationDetailResponse.setStatus(status);
        return validationDetailResponse;
    }

    private Map<Long, InventoryValidationRequestDetailDto> buildRequestMap(
            final List<InventoryValidationRequestDetailDto> request) {
        return request.stream()
            .collect(Collectors.toMap(InventoryValidationRequestDetailDto::getBeerId, Function.identity()));
    }

    private Map<Long, Integer> buildBeerInventoryMap(final List<InventoryBeerDto> inventoryBeers) {
        return inventoryBeers
            .stream()
            .collect(Collectors.toMap(InventoryBeerDto::getId, InventoryBeerDto::getQuantity));
    }
}
