package com.worldgdp.service;

import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;
import com.worldgdp.repository.CountryRepository;
import com.worldgdp.service.util.CountryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Resource
    CountryRepository countryRepository;

    CountryMapper countryMapper;

    private static final int PAGE_SIZE = 1;

    @Override
    public Optional<List<Country>> getCountries(Map<String, String> params) {

        log.info("Entering get countries method --> {}", params);
        int pageNo = 1;

        if(params.containsKey("pageNo"))
            pageNo = Integer.parseInt(params.get("pageNo"));

        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);

        return countryRepository.findCountries(
                StringUtils.hasText(params.get("search")) ? params.get("search"): "",
                StringUtils.hasText(params.get("continent")) ? params.get("search"): "",
                StringUtils.hasText(params.get("region")) ? params.get("search"): "",
                pageable
                );
    }

    @Override
    public List<Country> getCountryByName(String name) {

        log.info("Entering get countries by name method --> {}", name);
        return countryRepository.findByName(name);
    }

    @Override
    public List<Country> getCountryContinent(String continent) {
        int pageNo = 1;
        log.info("Entering get countries continent method --> {}", continent);
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE);

        return countryRepository.findByContinent(continent, pageable);
    }

    @Override
    public Country getCountryByCode(String code) {

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
}
