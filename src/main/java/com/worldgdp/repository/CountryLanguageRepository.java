package com.worldgdp.repository;

import com.worldgdp.models.City;
import com.worldgdp.models.CountryLanguage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CountryLanguageRepository extends PagingAndSortingRepository<CountryLanguage, Long> {

    List<CountryLanguage> findByCountrycode(String countryCode, Pageable pageable);

    List<CountryLanguage> findByCountrycode(String countryCode);


}
