package relay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.mapper.InventoryMapper;
import relay.repository.InventoryRepository;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/inventories")
public record InventoryController(
        InventoryMapper inventoryMapper,
        InventoryRepository inventoryRepository) {

    @GetMapping
    public List<InventoryBeerDto> getInventoryBeers(@RequestParam final Set<Long> beerIds) {
        return inventoryMapper.toInventoryBeerDtoList(inventoryRepository.findByBeerIds(beerIds));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody final InventoryCreationDto inventoryDto) {
        inventoryRepository.save(inventoryMapper.toInventory(inventoryDto));
    }
}
