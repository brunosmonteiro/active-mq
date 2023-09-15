package relay.mapper;

import org.mapstruct.Mapper;
import relay.entity.beer.Beer;
import shared.dto.beer.BeerCreationDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeerMapper {
    List<Beer> toBeerList(final List<BeerCreationDto> beerCreationDtoList);
}
