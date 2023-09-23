package relay.mapper;

import org.mapstruct.Mapper;

import relay.entity.beer.Beer;
import shared.dto.beer.BeerDto;
import shared.dto.beer.BeerRegistryDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeerMapper {
    List<Beer> toBeerList(final List<BeerRegistryDto> beerCreationDtoList);
    List<BeerDto> toBeerDtoList(final List<Beer> beers);
}
