package relay.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import relay.mapper.InventoryMapper;
import relay.repository.InventoryRepository;
import shared.client.InventoryClient;
import shared.dto.inventory.InventoryBeerDto;
import shared.dto.inventory.update.InventoryUpsertDto;

@RestController
@RequestMapping("/inventories")
public class InventoryController implements InventoryClient {
    private final InventoryMapper inventoryMapper;
    private final InventoryRepository inventoryRepository;

    public InventoryController(
            final InventoryMapper inventoryMapper,
            final InventoryRepository inventoryRepository) {
        this.inventoryMapper = inventoryMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @GetMapping
    public List<InventoryBeerDto> getInventoryBeers(
            @RequestParam(required = false) final Set<Long> beerIds,
            @RequestParam(required = false) final Set<String> beerExternalIds) {
        if (!CollectionUtils.isEmpty(beerIds)) {
            return inventoryMapper.toInventoryBeerDtoList(inventoryRepository.findByBeerIds(beerIds));
        }
        return inventoryMapper.toInventoryBeerDtoList(inventoryRepository.findByBeerExternalIds(beerExternalIds));
    }

    @Override
    @PostMapping
    @Transactional
    public void upsertInventories(@RequestBody final List<InventoryUpsertDto> inventoryUpdateDtoList) {
        final var inventories = inventoryRepository.findByBeerIds(extractBeerIds(inventoryUpdateDtoList));
        final Map<Long, InventoryUpsertDto> updateMap = mapBeerIdsInventoryUpdateDto(inventoryUpdateDtoList);
        inventories.forEach(inventory ->
            inventory.setQuantity(updateMap.get(inventory.getBeer().getId()).getQuantity()));
        inventoryRepository.saveAll(inventories);
    }

    private Set<Long> extractBeerIds(final List<InventoryUpsertDto> inventoryUpdateDtoList) {
        return inventoryUpdateDtoList.stream().map(InventoryUpsertDto::getBeerId).collect(Collectors.toSet());
    }

    private Map<Long, InventoryUpsertDto> mapBeerIdsInventoryUpdateDto(final List<InventoryUpsertDto> inventoryUpdateDtoList) {
        return inventoryUpdateDtoList.stream()
            .collect(Collectors.toMap(InventoryUpsertDto::getBeerId, Function.identity()));
    }
}
