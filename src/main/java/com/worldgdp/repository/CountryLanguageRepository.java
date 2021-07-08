package com.worldgdp.repository;

import com.worldgdp.models.CountryLanguage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryLanguageRepository extends PagingAndSortingRepository<CountryLanguage, Long> {
}
