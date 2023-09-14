package inventory.service;

import inventory.mapper.InventoryMapper;
import inventory.producer.InventoryErrorProducer;
import inventory.producer.InventoryValidatedProducer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryActionType;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.inventory.update.InventoryUpdateDto;
import shared.dto.inventory.validation.InventoryValidatedDto;
import shared.dto.inventory.validation.InventoryValidationDto;
import shared.dto.inventory.validation.InventoryValidationRequestDto;
import shared.dto.inventory.validation.InventoryValidationResponseDto;
import shared.dto.inventory.validation.InventoryValidationStatus;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void updateStock(final List<InventoryUpdateDto> updateList) {
        final var inventoryBeers =
            inventoryClient.getInventoryBeersByExternalId(extractBeerExternalIds(updateList));
        final Map<String, InventoryBeerDto> externalBeerMap = mapExternalIdsToInventoryBeers(inventoryBeers);
        updateInventory(
            inventoryBeers,
            updateList,
            inventory -> externalBeerMap.get(inventory.getBeerExternalId()),
            InventoryActionType.STOCK_UPDATE);
    }

    public void consumeStock(final OrderResponseDto order) {
        final var inventoryBeers =
            inventoryClient.getInventoryBeersById(extractBeerIds(order));
        final Map<Long, InventoryBeerDto> updateDtoMap = mapIdsToInventoryBeers(inventoryBeers);
        updateInventory(
            inventoryBeers,
            getUpdateList(order),
            inventory -> updateDtoMap.get(inventory.getBeerId()),
            InventoryActionType.CONSUMPTION);
    }

    private void updateInventory(
            final List<InventoryBeerDto> inventoryBeerDtoList,
            final List<InventoryUpdateDto> inventoryUpdateDtoList,
            final Function<InventoryUpdateDto, InventoryBeerDto> beerFunction,
            final InventoryActionType inventoryActionType) {
        if (CollectionUtils.isEmpty(inventoryBeerDtoList)) {
            throw new IllegalArgumentException("Inventory Beers cannot be empty.");
        }
        final List<InventoryErrorDto> errorList = new ArrayList<>();
        final var inventoryUpdate= prepareInventoryUpdate(
                beerFunction,
                inventoryUpdateDtoList,
                errorList);
        if (!errorList.isEmpty()) {
            errorList.forEach(error -> error.setActionType(inventoryActionType));
            inventoryErrorProducer.sendMessage(errorList);
        }
        inventoryClient.updateInventories(inventoryUpdate);
    }

    private List<InventoryUpdateDto> getUpdateList(final OrderResponseDto order) {
        return order.getBeers().stream().map(beer ->
            new InventoryUpdateDto(beer.getId(), beer.getQuantity())).toList();
    }

    private Set<String> extractBeerExternalIds(final List<InventoryUpdateDto> updateList) {
        return updateList.stream()
            .map(InventoryUpdateDto::getBeerExternalId)
            .collect(Collectors.toSet());
    }

    private Set<Long> extractBeerIds(final OrderResponseDto orderResponseDto) {
        return orderResponseDto.getBeers().stream()
            .map(OrderBeerResponseDto::getId)
            .collect(Collectors.toSet());
    }

    private Map<String, InventoryBeerDto> mapExternalIdsToInventoryBeers(final List<InventoryBeerDto> inventoryBeers) {
        return inventoryBeers.stream()
            .collect(Collectors.toMap(InventoryBeerDto::getExternalId, Function.identity()));
    }

    private Map<Long, InventoryBeerDto> mapIdsToInventoryBeers(final List<InventoryBeerDto> inventoryBeers) {
        return inventoryBeers.stream()
            .collect(Collectors.toMap(InventoryBeerDto::getId, Function.identity()));
    }

    private Integer getTotalQuantity(
            final InventoryBeerDto inventoryBeerDto,
            final InventoryUpdateDto inventoryExternalUpdateDto) {
        return ofNullable(inventoryBeerDto)
            .map(InventoryBeerDto::getQuantity)
            .orElse(0) + inventoryExternalUpdateDto.getQuantity();
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

    private List<InventoryUpdateDto> prepareInventoryUpdate(
            final Function<InventoryUpdateDto, InventoryBeerDto> beerFunction,
            final List<InventoryUpdateDto> update,
            final List<InventoryErrorDto> errorList) {
        return update.stream().map(requestBeer -> {
            final var beer = beerFunction.apply(requestBeer);
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
}
