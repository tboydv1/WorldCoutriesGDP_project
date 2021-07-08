package com.worldgdp.repository;

import com.worldgdp.models.City;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {

    List<City> findByCountryCode(String countryCode, Pageable pageable);




}
