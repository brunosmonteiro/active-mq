package inventory.service;

import inventory.producer.InventoryErrorProducer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import shared.dto.inventory.InventoryConsumptionErrorDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.dto.order.OrderBeerResponseDto;
import shared.dto.order.OrderResponseDto;
import shared.entity.beer.Beer;
import shared.entity.inventory.Inventory;
import shared.entity.order.OrderBeer;
import shared.repository.BeerRepository;
import shared.repository.InventoryRepository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryErrorProducer inventoryErrorProducer;

    public InventoryService(
            final InventoryRepository inventoryRepository,
            final InventoryErrorProducer inventoryErrorProducer) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryErrorProducer = inventoryErrorProducer;
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
