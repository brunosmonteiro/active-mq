package relay.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.entity.inventory.Inventory;
import relay.mapper.InventoryMapper;
import relay.repository.BeerRepository;
import relay.repository.InventoryRepository;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.InventoryCreationDto;
import shared.dto.inventory.update.InventoryUpdateDto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventories")
public class InventoryController implements InventoryClient {
    private final InventoryMapper inventoryMapper;
    private final InventoryRepository inventoryRepository;
    private final BeerRepository beerRepository;

    public InventoryController(
            final InventoryMapper inventoryMapper,
            final InventoryRepository inventoryRepository,
            final BeerRepository beerRepository) {
        this.inventoryMapper = inventoryMapper;
        this.inventoryRepository = inventoryRepository;
        this.beerRepository = beerRepository;
    }

    @Override
    @GetMapping
    public List<InventoryBeerDto> getInventoryBeers(
            @RequestParam(required = false) final Set<Long> beerIds,
            @RequestParam(required = false) final Set<String> beerExternalIds) {
        return inventoryMapper.toInventoryBeerDtoList(inventoryRepository.findByBeerIds(beerIds));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void createInventory(@RequestBody final List<InventoryCreationDto> inventoryDtoList) {
        final var inventories = inventoryDtoList.stream().map(inventoryDto ->
            new Inventory(
                beerRepository.findById(inventoryDto.getBeerId()).orElseThrow(),
                inventoryDto.getQuantity()
            )
        ).toList();
        inventoryRepository.saveAll(inventories);
    }

    @Override
    @PutMapping
    @Transactional
    public void updateInventories(@RequestBody final List<InventoryUpdateDto> inventoryUpdateDtoList) {
        final var inventories = inventoryRepository.findByBeerIds(extractBeerIds(inventoryUpdateDtoList));
        final Map<Long, InventoryUpdateDto> updateMap = mapBeerIdsInventoryUpdateDto(inventoryUpdateDtoList);
        inventories.forEach(inventory ->
            inventory.setQuantity(updateMap.get(inventory.getBeer().getId()).getQuantity()));
        inventoryRepository.saveAll(inventories);
    }

    private Set<Long> extractBeerIds(final List<InventoryUpdateDto> inventoryUpdateDtoList) {
        return inventoryUpdateDtoList.stream().map(InventoryUpdateDto::getBeerId).collect(Collectors.toSet());
    }

    private Map<Long, InventoryUpdateDto> mapBeerIdsInventoryUpdateDto(final List<InventoryUpdateDto> inventoryUpdateDtoList) {
        return inventoryUpdateDtoList.stream()
            .collect(Collectors.toMap(InventoryUpdateDto::getBeerId, Function.identity()));
    }
}
