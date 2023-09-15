package relay.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import relay.entity.pricing.Pricing;
import relay.mapper.PricingMapper;
import relay.repository.BeerRepository;
import relay.repository.PricingRepository;
import shared.client.PricingClient;
import shared.dto.pricing.PricingCreationDto;
import shared.dto.pricing.PricingInfoResponseDto;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/prices")
public class PricingController implements PricingClient {
    private final PricingRepository pricingRepository;
    private final PricingMapper pricingMapper;
    private final BeerRepository beerRepository;

    public PricingController(
            final PricingRepository pricingRepository,
            final PricingMapper pricingMapper,
            final BeerRepository beerRepository) {
        this.pricingRepository = pricingRepository;
        this.pricingMapper = pricingMapper;
        this.beerRepository = beerRepository;
    }

    @Override
    @GetMapping
    public List<PricingInfoResponseDto> getPricing(@RequestParam final Set<Long> beerIds) {
        return pricingMapper.toPricingInfoResponseDtoList(pricingRepository.findByBeerIds(beerIds));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void registerPrices(@RequestBody final List<PricingCreationDto> pricingCreationDtoList) {
        final var prices = pricingCreationDtoList.stream().map(pricingDto ->
            new Pricing(
                beerRepository.findById(pricingDto.getBeerId()).orElseThrow(),
                pricingDto.getUnitPrice()
            )
        ).toList();
        pricingRepository.saveAll(prices);
    }
}
