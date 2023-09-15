package relay.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import relay.entity.pricing.Pricing;
import shared.dto.pricing.PricingInfoResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PricingMapper {
    @Mapping(target = "beerId", source = "beer.id")
    PricingInfoResponseDto toPricingInfoResponseDto(final Pricing pricing);

    List<PricingInfoResponseDto> toPricingInfoResponseDtoList(final List<Pricing> pricingList);
}
