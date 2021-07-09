package com.worldgdp.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;
import com.worldgdp.repository.CountryRepository;
import com.worldgdp.service.util.CountryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {


    @Resource
    CountryRepository countryRepository;

    @Autowired
    CountryMapper countryMapper;

    private static final int PAGE_SIZE = 10;

    ObjectMapper mapper;

    public CountryServiceImpl(){
        mapper  = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }


    @Override
    public List<Country> getCountries(Map<String, Object> params) {

        log.info("Entering get countries method --> {}", params);
        int pageNo = 1;

        if(params.get("search") == null && params.get("continent") == null && params.get("region") == null){
            return countryRepository.findAll();
        }

        if(params.containsKey("pageNo"))
            pageNo = Integer.parseInt(params.get("pageNo").toString());

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);

        Optional<List<Country>> result = countryRepository.findCountries(
                StringUtils.hasText(params.get("search").toString()) ? params.get("search").toString(): "",
                StringUtils.hasText(params.get("continent").toString()) ? params.get("continent").toString(): "",
                StringUtils.hasText(params.get("region").toString()) ? params.get("region").toString(): "",
                pageable
        );



        return result.orElse(Collections.emptyList());
    }

    @Override
    public List<Country> findByName(String name) {

        log.info("Entering get countries by name method --> {}", name);
        return countryRepository.findByName(name);
    }

    @Override
    public List<Country> findByContinent(String continent) {
        int pageNo = 1;
        log.info("Entering get countries continent method --> {}", continent);
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);

        return countryRepository.findByContinent(continent, pageable);
    }

    @Override
    public Country findByCode(String code) {

        log.info("Entering get countries by code method --> {}", code);
        return countryRepository.findByCode(code);
    }

    @Override
    public Country editCountry(String countryCode, CountryDto country) throws Exception {

        Country savedCountry = countryRepository.findByCode(countryCode);

        if(country != null){
            countryMapper.mapCountry(country, savedCountry);
            return countryRepository.save(savedCountry);

        }else{
            throw new Exception("Country is null --> ");

        }

    }

    @Override
    public List<String> getContinents() {
        return countryRepository.getContinents().orElse(Collections.emptyList());
    }

    @Override
    public List<String> getRegions() {
        return countryRepository.getRegions().orElse(Collections.emptyList());
    }

    @Override
    public int getCountriesCount(Map<String, Object> params) {

        if(params.get("search") == null && params.get("continent") == null && params.get("region") == null){
            return countryRepository.getCountriesCount();
        }

        return countryRepository.getCountriesCount(
                StringUtils.hasText(params.get("search").toString()) ? params.get("search").toString(): "",
                StringUtils.hasText(params.get("continent").toString()) ? params.get("continent").toString(): "",
                StringUtils.hasText(params.get("region").toString()) ? params.get("region").toString(): "");
    }

    @Override
    public List<String> getGovernmentTypes() {
        return countryRepository.getGovernmentTypes().orElse(Collections.emptyList());

    }

    @Override
    public List<String> getHeadOfStates() {
        return countryRepository.getHeadOfStates().orElse(Collections.emptyList());
    }
}
