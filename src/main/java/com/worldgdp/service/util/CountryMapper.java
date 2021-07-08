package com.worldgdp.service.util;

import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCountry(CountryDto countryDto,
                    @MappingTarget Country employee);
}
