package destination.controller;

import destination.producer.InventoryExternalProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.dto.inventory.update.InventoryUpdateDto;

@RestController
@RequestMapping("/external/inventories")
public record InventoryExternalController(InventoryExternalProducer inventoryExternalProducer) {

    @PostMapping
    public void publishEvent(@RequestBody final InventoryUpdateDto updateDto) {
        inventoryExternalProducer.sendMessage(updateDto);
    }
}
