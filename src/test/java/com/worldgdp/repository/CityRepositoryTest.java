package com.worldgdp.repository;

import com.worldgdp.models.City;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = {"classpath:db/insert-data.sql"})
@Slf4j
class CityRepositoryTest {

    @Autowired
    CityRepository cityRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testGetCities(){
        Pageable firstPage = PageRequest.of(1, 10);
        Iterable<City> cities = cityRepository.findByCountryCode("IND", firstPage);
        assertThat(cities).hasSize(10);
    }

    @Test public void testGetCityDetail(){
        Long cityId = 1024l;
        Optional<City> city = cityRepository.findById(cityId);
        assertTrue(city.isPresent());
        log.info("Found city --> {}", city.get());
        assertThat(city.get().toString()).
                isEqualTo("City{id=1024, name='Mumbai (Bombay)', " +
                        "countryCode='IND', country=null, district='Maharashtra', population=10500000}");
    }

    @Test
    public void testAddCity(){

        String countryCode =  "IND";
        City city = new City();
        city.setCountryCode(countryCode);
        city.setDistrict("District");
        city.setName("City Name");
        city.setPopulation(101010L);
        Long cityId = cityRepository.save(city).getId();
        assertThat(cityId).isNotNull();
//        City cityFromDb = cityRepository.findById(cityId).orElse(null);
//        assertThat(cityFromDb).isNotNull();
//        assertThat(cityFromDb.getName()).isEqualTo("City Name");
    }
}