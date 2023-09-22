package shared.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shared.dto.beer.BeerDto;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;
import java.util.Set;

@FeignClient(value = "BeerClient", url = "${url.resources-relay}/beers")
public interface BeerClient {
    @GetMapping
    List<BeerDto> getBeers(
        @RequestParam(required = false) final Set<String> beerExternalIds,
        @RequestParam(required = false) final Set<Long> beerIds);

    default List<BeerDto> getBeersByExternalId(final Set<String> beerExternalIds) {
        return getBeers(beerExternalIds, null);
    }

    default List<BeerDto> getBeersById(final Set<Long> beerIds) {
        return getBeers(null, beerIds);
    }

    @PostMapping
    void createBeer(@RequestBody final List<BeerRegistryDto> beers);
}
