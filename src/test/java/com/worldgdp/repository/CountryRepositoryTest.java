package com.worldgdp.repository;

import com.worldgdp.WorldgdpApplication;
import com.worldgdp.models.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {WorldgdpApplication.class})
@Slf4j
@Sql(scripts = "classpath:db/insert-data.sql")
class CountryRepositoryTest {

    @Resource
    CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetCountries(){

        Pageable firstPage = PageRequest.of(1, 20);
        Iterable<Country> countryList = countryRepository.findAll(firstPage);
        assertThat(countryList).hasSize(20);

    }

    @Test
    void testGetCountries_searchByName(){
        Iterable<Country> countries = countryRepository.findByName("Aruba");
        assertThat(countries).hasSize(1);
    }

    @Test
    void testGetCountries_searchByContinent(){
        Pageable firstPage = PageRequest.of(1, 20);

        Iterable<Country> countriesByContinent = countryRepository.findByContinent("Asia", firstPage);
        assertThat(countriesByContinent).hasSize(20);

    }


    @Test
    void testGetCountries_searchByCode(){
        Country countriesByCode = countryRepository.findByCode("IND");
        assertThat(countriesByCode).isNotNull();
        assertThat(countriesByCode.getName()).isEqualTo("India");
        assertThat(countriesByCode.getContinent()).isEqualTo("Asia");
        assertThat(countriesByCode.getCode()).isEqualTo("IND");

    }

    @Test
    void testEditCountryDetails(){
        Country countriesByCode = countryRepository.findByCode("IND");
        assertThat(countriesByCode).isNotNull();
        assertThat(countriesByCode.getCode()).isEqualTo("IND");

        log.info("Updating country details --> {}", countriesByCode);
        countriesByCode.setHeadOfState("Ram Nath Kovind");
        countriesByCode.setPopulation(1324171354L);

        countryRepository.save(countriesByCode);
        Country c = countryRepository.findByCode("IND");

        assertThat(c).isNotNull();
        assertThat(c.getCode()).isEqualTo("IND");
        assertThat(c.getHeadOfState()).isEqualTo("Ram Nath Kovind");
        assertThat(c.getPopulation()).isEqualTo(1324171354L);



    }

    @Test
    public void findCountryBySearchTermTest(){
        Pageable firstPage = PageRequest.of(1, 5);

        Optional<List<Country>> countriesBySearch = countryRepository.findBySearch("No".toLowerCase(), firstPage);
        log.info("Size of countries --> {}", countriesBySearch);

        assertFalse(countriesBySearch.isEmpty());
//        assertThat(countriesBySearch.get().get(0).getName().toLowerCase().contains("no"));
        assertThat(countriesBySearch.get().size()).isEqualTo(5);

    }


    @Test
    public void findBySearchTermAndContinentTest(){

        Pageable firstPage = PageRequest.of(1, 1);

        Optional<List<Country>> countriesBySearch = countryRepository.
                findCountries("No".toLowerCase(), "Asia", "Eastern Asia", firstPage);
        log.info("Size of countries --> {}", countriesBySearch);

        assertFalse(countriesBySearch.isEmpty());



    }



}