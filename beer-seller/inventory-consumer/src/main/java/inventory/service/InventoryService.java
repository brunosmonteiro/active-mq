package inventory.service;

import inventory.mapper.InventoryMapper;
import inventory.producer.InventoryErrorProducer;
import inventory.producer.InventoryValidatedProducer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryBeerConsumptionErrorDto;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryConsumptionErrorDto;
import shared.dto.inventory.update.InventoryExternalUpdateDto;
import shared.dto.inventory.update.InventoryUpdateDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.inventory.validation.InventoryValidatedDto;
import shared.dto.inventory.validation.InventoryValidationDto;
import shared.dto.inventory.validation.InventoryValidationRequestDto;
import shared.dto.inventory.validation.InventoryValidationResponseDto;
import shared.dto.inventory.validation.InventoryValidationStatus;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class InventoryService {
    private final InventoryMapper inventoryMapper;
    private final InventoryClient inventoryClient;
    private final InventoryErrorProducer inventoryErrorProducer;
    private final InventoryValidatedProducer inventoryValidatedProducer;

    public InventoryService(
            final InventoryMapper inventoryUpdateMapper,
            final InventoryClient inventoryClient,
            final InventoryErrorProducer inventoryErrorProducer,
            final InventoryValidatedProducer inventoryValidatedProducer) {
        this.inventoryMapper = inventoryUpdateMapper;
        this.inventoryClient = inventoryClient;
        this.inventoryErrorProducer = inventoryErrorProducer;
        this.inventoryValidatedProducer = inventoryValidatedProducer;
    }

    public void updateStock(final List<InventoryExternalUpdateDto> update) {
        final var beerExternalIds = extractBeerExternalIds(update);
        final List<InventoryBeerDto> inventoryBeers = inventoryClient.getInventoryBeersByExternalId(beerExternalIds);
        if (CollectionUtils.isEmpty(inventoryBeers)) {
            throw new IllegalArgumentException("Inventory Beers cannot be empty.");
        }
        final Map<String, InventoryBeerDto> externalBeerMap = mapExternalIdsToInventoryBeers(inventoryBeers);
        final List<InventoryErrorDto> errorList = new ArrayList<>();
        final var inventoryUpdate= prepareInventoryUpdate(externalBeerMap, update, errorList);
        if (!errorList.isEmpty()) {
            inventoryErrorProducer.sendMessage(errorList);
        }
        inventoryClient.updateInventories(inventoryUpdate);
    }

    private Set<String> extractBeerExternalIds(final List<InventoryExternalUpdateDto> update) {
        return update.stream()
            .map(InventoryExternalUpdateDto::getBeerExternalId)
            .collect(Collectors.toSet());
    }

    private Map<String, InventoryBeerDto> mapExternalIdsToInventoryBeers(final List<InventoryBeerDto> inventoryBeers) {
        return inventoryBeers.stream()
            .collect(Collectors.toMap(InventoryBeerDto::getExternalId, Function.identity()));
    }

    private Integer getTotalQuantity(
            final InventoryBeerDto inventoryBeerDto,
            final InventoryExternalUpdateDto inventoryExternalUpdateDto) {
        return ofNullable(inventoryBeerDto)
            .map(InventoryBeerDto::getQuantity)
            .orElse(0) + inventoryExternalUpdateDto.getQuantity();
    }

    private Integer getTotalQuantity(
            final InventoryBeerDto inventoryBeerDto,
            final OrderBeerResponseDto orderBeerResponseDto) {
        return ofNullable(inventoryBeerDto)
                .map(InventoryBeerDto::getQuantity)
                .orElse(0) + orderBeerResponseDto.getQuantity();
    }

    public void validateStock(final InventoryValidationRequestDto request) {
        final Set<Long> beerIds = request.getValidations().stream()
            .map(InventoryValidationDto::getBeerId).collect(Collectors.toSet());
        final var inventory = inventoryClient.getInventoryBeersById(beerIds);
        final var validation = getValidation(request, inventory);
        inventoryValidatedProducer.sendMessage(validation);
    }

    private InventoryValidationResponseDto getValidation(
            final InventoryValidationRequestDto request,
            final List<InventoryBeerDto> inventoryBeers) {
        final Map<Long, InventoryValidationDto> requestMap = buildRequestMap(request);
        final Map<Long, Integer> beerInventoryMap = buildBeerInventoryMap(inventoryBeers);
        final List<InventoryValidatedDto> response = validateInventory(requestMap, beerInventoryMap);
        return new InventoryValidationResponseDto(response);
    }

    private Map<Long, InventoryValidationDto> buildRequestMap(final InventoryValidationRequestDto request) {
        return request.getValidations()
            .stream()
            .collect(Collectors.toMap(InventoryValidationDto::getBeerId, Function.identity()));
    }

    private Map<Long, Integer> buildBeerInventoryMap(final List<InventoryBeerDto> inventoryBeers) {
        return inventoryBeers
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
        final var inventoryBeers = inventoryClient.getInventoryBeersById(beerMap.keySet());
        final List<InventoryBeerConsumptionErrorDto> consumptionErrorList = new ArrayList<>();
        final var inventoriesUpdate = prepareInventoryUpdate(beerMap, beerMap.values(), consumptionErrorList)
        inventoryClient.updateInventories();
    }

    private void sendErrorMessage(final Inventory inventory, final OrderBeerResponseDto beerOrder, final Long orderId) {
        final var error = new InventoryConsumptionErrorDto();
        error.setAvailable(inventory.getQuantity());
        error.setRequested(beerOrder.getQuantity());
        error.setOrderId(orderId);
        inventoryErrorProducer.sendMessage(error);
    }

    private List<InventoryUpdateDto> prepareInventoryUpdate(
            final Map<String, InventoryBeerDto> externalBeerMap,
            final List<InventoryExternalUpdateDto> update,
            final List<InventoryErrorDto> errorList) {
        return update.stream().map(requestBeer -> {
            final var beer = externalBeerMap.get(requestBeer.getBeerExternalId());
            final var totalQuantity = getTotalQuantity(beer, requestBeer);
            if (beer == null || totalQuantity < 0) {
                errorList.add(inventoryMapper.toInventoryUpdateErrorDto(beer, requestBeer));
                return null;
            }
            return new InventoryUpdateDto(beer.getId(), totalQuantity);
        })
        .filter(Objects::nonNull)
        .toList();
    }
    private List<InventoryUpdateDto> prepareInventoryUpdate(
            final Map<Long, InventoryBeerDto> beerMap,
            final List<OrderBeerResponseDto> update,
            final List<InventoryConsumptionErrorDto> errorList) {
        return update.stream().map(requestBeer -> {
            final var beer = beerMap.get(requestBeer.getId());
            final var totalQuantity = getTotalQuantity(beer, requestBeer);
            if (beer == null || totalQuantity < 0) {
                errorList.add(inventoryMapper.toInventoryConsumptionErrorDto(beer, requestBeer));
                return null;
            }
            return new InventoryUpdateDto(beer.getId(), totalQuantity);
        })
        .filter(Objects::nonNull)
        .toList();
    }

}
