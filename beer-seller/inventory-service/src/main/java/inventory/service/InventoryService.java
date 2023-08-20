package inventory.service;

import inventory.producer.InventoryProducer;
import org.springframework.stereotype.Service;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.InventoryResponseDto;
import shared.dto.inventory.InventoryUpdateDto;
import shared.mapper.InventoryMapper;
import shared.repository.InventoryRepository;

import java.util.Set;

@Service
public record InventoryService(
        InventoryRepository inventoryRepository,
        InventoryMapper inventoryMapper,
        InventoryProducer inventoryProducer) {
    public InventoryResponseDto getInventory(final Set<Long> beerIds) {
        return inventoryMapper.toInventoryResponseDto(inventoryRepository.findByBeerIds(beerIds));
    }

    public void publishEvent(final InventoryUpdateDto updateDto) {
        inventoryProducer.sendOrder(updateDto);
    }

    public void createInventory(final InventoryCreationDto inventoryDto) {
        final var inventory = inventoryMapper.toInventory(inventoryDto);
        inventory.setQuantity(0);
        inventoryRepository.save(inventory);
    }
}
