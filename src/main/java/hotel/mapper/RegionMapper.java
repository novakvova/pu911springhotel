package hotel.mapper;

import hotel.dto.regions.AddRegionDto;
import hotel.entities.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    Region AddRegionToRegion(AddRegionDto addRegionDto);
}
