package inventory.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import shared.dto.inventory.InventoryUpdateDto;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.inventory.Inventory;
import shared.mapper.InventoryMapper;
import shared.repository.InventoryRepository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryService(final InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
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
