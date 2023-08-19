package inventory.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import shared.dto.beer.InventoryUpdateDto;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.inventory.Inventory;
import shared.mapper.InventoryMapper;
import shared.repository.InventoryRepository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public record InventoryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
    @Transactional
    public void updateStock(final InventoryUpdateDto update) {
        final Inventory inventory = inventoryRepository.findByBeerExternalId(update.getBeerId());
        if (inventory == null) {
            final Inventory newEntry = inventoryMapper.toInventory(update);
            inventoryRepository.save(newEntry);
        } else {
            inventory.setQuantity(inventory.getQuantity() + update.getQuantity());
            inventoryRepository.save(inventory);
        }
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
                // exception?
            }
            inventory.setQuantity(newQuantity);
        });
        inventoryRepository.saveAll(inventories);
    }
}
