package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@FeignClient(value = "BeerClient", url = "${url.resources-relay}/beers")
public interface BeerClient {
    @PostMapping
    void createBeer(@RequestBody final List<BeerRegistryDto> beers);
}
