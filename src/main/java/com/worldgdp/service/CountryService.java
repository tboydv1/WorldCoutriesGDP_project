package com.worldgdp.service;

import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;

import java.util.List;
import java.util.Map;

public interface CountryService {

    List<Country> getCountries(Map<String, Object> params);

    List<Country> findByName(String name);

    List<Country> findByContinent(String continent);

    Country findByCode(String code);

    Country editCountry(String countryCode, CountryDto country) throws Exception;


    List<String> getContinents();

    List<String> getRegions();

    int getCountriesCount(Map<String, Object> params);

    List<String> getGovernmentTypes();

    List<String> getHeadOfStates();

}
