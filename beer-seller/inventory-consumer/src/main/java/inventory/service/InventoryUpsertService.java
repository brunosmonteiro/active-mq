package inventory.service;

import inventory.mapper.InventoryMapper;
import inventory.producer.InventoryErrorProducer;
import org.springframework.stereotype.Service;
import shared.client.BeerClient;
import shared.client.InventoryClient;
import shared.constants.inventory.InventoryActionType;
import shared.dto.beer.BeerDto;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.update.InventoryUpsertDto;
import shared.dto.inventory.update.InventoryErrorDto;
import shared.dto.order.event.OrderBeerEventDto;
import shared.dto.order.event.OrderEventDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public record InventoryUpsertService(
        InventoryMapper inventoryMapper,
        InventoryClient inventoryClient,
        BeerClient beerClient,
        InventoryErrorProducer inventoryErrorProducer) {

    public void updateStock(final List<InventoryUpsertDto> updateList) {
        final var beers = beerClient.getBeersByExternalId(extractBeerExternalIds(updateList));
        final var validInventories = getValidInventories(beers, updateList);
        if (containsBeerNotFound(beers, updateList)) {
            postInvalidInventories(updateList, validInventories);
        }
        if (!validInventories.isEmpty()) {
            inventoryClient.upsertInventories(validInventories);
        }
    }

    public void consumeStock(final OrderEventDto order) {
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
            final List<InventoryUpsertDto> inventoryUpdateDtoList,
            final Function<InventoryUpsertDto, InventoryBeerDto> beerFunction,
            final InventoryActionType inventoryActionType) {
        final List<InventoryErrorDto> errorList = new ArrayList<>();
        final var inventoryUpdate =
                prepareInventoryUpdate(beerFunction, inventoryUpdateDtoList, errorList);
        if (!errorList.isEmpty()) {
            errorList.forEach(error -> error.setActionType(inventoryActionType));
            inventoryErrorProducer.sendMessage(errorList);
        }
        inventoryClient.upsertInventories(inventoryUpdate);
    }

    private List<InventoryUpsertDto> getUpdateList(final OrderEventDto order) {
        return order.getBeers().stream().map(beer ->
                new InventoryUpsertDto(beer.getBeerId(), beer.getQuantity())).toList();
    }

    private Set<String> extractBeerExternalIds(final List<InventoryUpsertDto> updateList) {
        return updateList.stream()
                .map(InventoryUpsertDto::getBeerExternalId)
                .collect(Collectors.toSet());
    }

    private Set<Long> extractBeerIds(final OrderEventDto order) {
        return order.getBeers().stream()
                .map(OrderBeerEventDto::getBeerId)
                .collect(Collectors.toSet());
    }

    private Map<Long, InventoryBeerDto> mapIdsToInventoryBeers(final List<InventoryBeerDto> inventoryBeers) {
        return inventoryBeers.stream()
                .collect(Collectors.toMap(InventoryBeerDto::getId, Function.identity()));
    }

    private List<InventoryUpsertDto> prepareInventoryUpdate(
            final Function<InventoryUpsertDto, InventoryBeerDto> beerFunction,
            final List<InventoryUpsertDto> update,
            final List<InventoryErrorDto> errorList) {
        return update.stream().map(requestBeer -> {
                    final var beer = beerFunction.apply(requestBeer);
                    final var totalQuantity = getTotalQuantity(beer, requestBeer);
                    if (beer == null || totalQuantity < 0) {
                        errorList.add(inventoryMapper.toInventoryUpdateErrorDto(beer, requestBeer));
                        return null;
                    }
                    return new InventoryUpsertDto(beer.getId(), totalQuantity);
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private Integer getTotalQuantity(
            final InventoryBeerDto inventoryBeerDto,
            final InventoryUpsertDto inventoryExternalUpdateDto) {
        return ofNullable(inventoryBeerDto)
            .map(InventoryBeerDto::getQuantity)
            .orElse(0) + inventoryExternalUpdateDto.getQuantity();
    }

    private List<InventoryUpsertDto> getValidInventories(
            final List<BeerDto> beers,
            final List<InventoryUpsertDto> updateDtoList) {
        final Map<String, BeerDto> beerExternalIdMap = beers.stream()
            .collect(Collectors.toMap(BeerDto::getExternalId, Function.identity()));
        return updateDtoList.stream()
            .filter(update -> beerExternalIdMap.containsKey(update.getBeerExternalId()))
            .toList();
    }

    private void postInvalidInventories(
            final List<InventoryUpsertDto> allInventories,
            final List<InventoryUpsertDto> validInventories) {
        final Map<String, InventoryUpsertDto> validInventoriesMap = validInventories.stream()
                .collect(Collectors.toMap(InventoryUpsertDto::getBeerExternalId, Function.identity()));
        final var invalidInventories = allInventories.stream()
            .filter(inventory -> !validInventoriesMap.containsKey(inventory.getBeerExternalId()))
            .map(inventory -> new InventoryErrorDto(
                InventoryActionType.STOCK_UPDATE,
                inventory.getBeerExternalId(),
                "Beer not found in the database"))
            .toList();
        inventoryErrorProducer.sendMessage(invalidInventories);

    }

    private boolean containsBeerNotFound(
            final List<BeerDto> beers,
            final List<InventoryUpsertDto> updateDtoList) {
        final Map<String, BeerDto> beerExternalIdMap = beers.stream()
            .collect(Collectors.toMap(BeerDto::getExternalId, Function.identity()));
        return !updateDtoList.stream()
            .allMatch(update -> beerExternalIdMap.containsKey(update.getBeerExternalId()));
    }
}
