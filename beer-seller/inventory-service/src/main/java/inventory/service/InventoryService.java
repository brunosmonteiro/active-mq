package inventory.service;

import org.springframework.stereotype.Service;
import shared.dto.inventory.InventoryResponseDto;
import shared.mapper.InventoryMapper;
import shared.repository.InventoryRepository;

import java.util.Set;

@Service
public record InventoryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
    public InventoryResponseDto getInventory(final Set<Long> beerIds) {
        return inventoryMapper.toInventoryResponseDto(inventoryRepository.findByBeerIds(beerIds));
    }
}
