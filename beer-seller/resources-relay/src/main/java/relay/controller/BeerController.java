package relay.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.mapper.BeerMapper;
import relay.repository.BeerRepository;
import shared.client.BeerClient;
import shared.dto.beer.BeerDto;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/beers")
public class BeerController implements BeerClient {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerController(final BeerRepository beerRepository, final BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void createBeer(@RequestBody final List<BeerRegistryDto> beers) {
        beerRepository.saveAll(beerMapper.toBeerList(beers));
    }

    @Override
    @GetMapping
    public List<BeerDto> getBeers(
            @RequestParam(required = false) final Set<String> beerExternalIds,
            @RequestParam(required = false) final Set<Long> beerIds) {
        if (!CollectionUtils.isEmpty(beerIds)) {
            return beerMapper.toBeerDtoList(beerRepository.findAllById(beerIds));
        }
        return beerMapper.toBeerDtoList(beerRepository.findByExternalIdIn(beerExternalIds));
    }
}
