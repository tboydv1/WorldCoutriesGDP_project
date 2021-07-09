package com.worldgdp.service;

import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CountryService {

    List<Country> getCountries(Map<String, String> params);

    List<Country> getCountryByName(String name);

    List<Country> getCountryContinent(String continent);

    Country getCountryByCode(String code);

    Country editCountry(String countryCode, CountryDto country) throws Exception;



}
