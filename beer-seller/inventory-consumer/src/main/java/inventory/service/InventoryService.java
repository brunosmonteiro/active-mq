package inventory.service;

import inventory.producer.InventoryErrorProducer;
import inventory.producer.InventoryValidatedProducer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryConsumptionErrorDto;
import shared.dto.inventory.InventoryResponseDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.dto.inventory.validation.InventoryValidatedDto;
import shared.dto.inventory.validation.InventoryValidationDto;
import shared.dto.inventory.validation.InventoryValidationRequestDto;
import shared.dto.inventory.validation.InventoryValidationResponseDto;
import shared.dto.inventory.validation.InventoryValidationStatus;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.beer.Beer;
import shared.entity.inventory.Inventory;
import shared.entity.order.OrderBeer;
import shared.repository.BeerRepository;
import shared.repository.InventoryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class InventoryService {
    private final InventoryClient inventoryClient;
    private final InventoryErrorProducer inventoryErrorProducer;
    private final InventoryValidatedProducer inventoryValidatedProducer;

    public InventoryService(
            final InventoryClient inventoryClient,
            final InventoryErrorProducer inventoryErrorProducer,
            final InventoryValidatedProducer inventoryValidatedProducer) {
        this.inventoryClient = inventoryClient;
        this.inventoryErrorProducer = inventoryErrorProducer;
        this.inventoryValidatedProducer = inventoryValidatedProducer;
    }

    @Transactional
    public void updateStock(final InventoryUpdateDto update) {
        final Inventory inventory = inventoryRepository.findByBeerExternalId(update.getBeerId());
        if (inventory == null) {
            throw new IllegalArgumentException();
        }
        inventory.setQuantity(inventory.getQuantity() + update.getQuantity());
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void validateStock(final InventoryValidationRequestDto request) {
        final Set<Long> beerIds = request.getValidations().stream()
            .map(InventoryValidationDto::getBeerId).collect(Collectors.toSet());
        final var inventory = inventoryClient.getInventory(beerIds);
        final var validation = getValidation(request, inventory);
        inventoryValidatedProducer.sendMessage(validation);
    }

    private InventoryValidationResponseDto getValidation(
            final InventoryValidationRequestDto request,
            final InventoryResponseDto inventoryResponseDto) {
        final Map<Long, InventoryValidationDto> requestMap = buildRequestMap(request);
        final Map<Long, Integer> beerInventoryMap = buildBeerInventoryMap(inventoryResponseDto);
        final List<InventoryValidatedDto> response = validateInventory(requestMap, beerInventoryMap);
        return new InventoryValidationResponseDto(response);
    }

    private Map<Long, InventoryValidationDto> buildRequestMap(final InventoryValidationRequestDto request) {
        return request.getValidations()
            .stream()
            .collect(Collectors.toMap(InventoryValidationDto::getBeerId, Function.identity()));
    }

    private Map<Long, Integer> buildBeerInventoryMap(final InventoryResponseDto inventoryResponseDto) {
        return inventoryResponseDto.getBeers()
            .stream()
            .collect(Collectors.toMap(InventoryBeerDto::getId, InventoryBeerDto::getQuantity));
    }

    private List<InventoryValidatedDto> validateInventory(
            final Map<Long, InventoryValidationDto> requestMap,
            final Map<Long, Integer> beerInventoryMap) {
        return requestMap.values()
            .stream()
            .map(requestBeer -> buildInventoryValidatedDto(requestBeer, beerInventoryMap))
            .toList();
    }

    private InventoryValidatedDto buildInventoryValidatedDto(
            final InventoryValidationDto requestBeer,
            final Map<Long, Integer> beerInventoryMap) {
        final var validated = new InventoryValidatedDto();
        Integer beerQuantity = ofNullable(beerInventoryMap.get(requestBeer.getBeerId())).orElse(0);
        validated.setOrderId(requestBeer.getOrderId());
        validated.setStatus(beerQuantity < requestBeer.getQuantity() ?
            InventoryValidationStatus.INVALID :
            InventoryValidationStatus.VALID);
        return validated;
    }

    @Transactional
    public void consumeStock(final OrderResponseDto order) {
        final Map<Long, OrderBeerResponseDto> beerMap =
                order.getBeers().stream().collect(Collectors.toMap(OrderBeerResponseDto::getId, Function.identity()));
        final var inventories = inventoryRepository.findByBeerIds(beerMap.keySet());
        inventories.forEach(inventory -> {
            final var beerOrder = beerMap.get(inventory.getBeer().getId());
            final var newQuantity = inventory.getQuantity() - beerOrder.getQuantity();
            if (newQuantity < 0) {
               sendErrorMessage(inventory, beerOrder, order.getId());
               return;
            }
            inventory.setQuantity(newQuantity);
        });
        inventoryRepository.saveAll(inventories);
    }

    private void sendErrorMessage(final Inventory inventory, final OrderBeerResponseDto beerOrder, final Long orderId) {
        final var error = new InventoryConsumptionErrorDto();
        error.setAvailable(inventory.getQuantity());
        error.setRequested(beerOrder.getQuantity());
        error.setOrderId(orderId);
        inventoryErrorProducer.sendMessage(error);
    }
}
