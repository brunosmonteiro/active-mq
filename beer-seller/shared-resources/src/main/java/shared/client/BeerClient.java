package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@FeignClient(value = "BeerClient", url = "${url.beer}")
public interface BeerClient {
    void createBeer(@RequestBody final List<BeerRegistryDto> beers);
}
