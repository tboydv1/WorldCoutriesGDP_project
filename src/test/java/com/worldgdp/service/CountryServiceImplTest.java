package com.worldgdp.service;

import com.worldgdp.WorldgdpApplication;
import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;
import com.worldgdp.repository.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.Resource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {WorldgdpApplication.class})
@Slf4j
@Sql(scripts = "classpath:db/insert-data.sql")
class CountryServiceImplTest {

    @Autowired
    CountryService countryServiceImpl;

    @Resource
    CountryRepository countryRepository;

    @BeforeEach
    void setUp() {

        CountryDto countryDto = new CountryDto();
        countryDto.setName("Netherlands");
        countryDto.setContinent("Europe");
        countryDto.setRegion("Western Europe");
        countryDto.setSurfaceArea(41526d);
        countryDto.setIndepYear((short) 1581);
        countryDto.setPopulation(15864000L);
        countryDto.setLifeExpectancy(78.3000031);
        countryDto.setGnp(371362.00);
        countryDto.setGnpold(360478.00);
        countryDto.setLocalName("Nederland");
        countryDto.setGovernmentForm("Constitutional Monarchy");
    }

    @Test
    void editCountryDetailTest() throws Exception {

        //get saved country
        String code = "NLD";
        CountryDto countryDto = new CountryDto();

        Country country = countryRepository.findByCode(code);
        log.info("Country object before update --> {}", country);

        assertThat(country.getName()).isEqualTo("Netherlands");
        assertThat(country.getContinent()).isEqualTo("Europe");
        assertThat(country.getRegion()).isEqualTo("Western Europe");
        assertThat(country.getHeadOfState()).isEqualTo("Beatrix");

        countryDto.setHeadOfState("Mark Rutte");

        Country updatedCountry = countryServiceImpl.editCountry(code, countryDto);
        log.info("Country object after update --> {}", updatedCountry);

        assertThat(updatedCountry.getName()).isEqualTo("Netherlands");
        assertThat(updatedCountry.getContinent()).isEqualTo("Europe");
        assertThat(updatedCountry.getRegion()).isEqualTo("Western Europe");
        assertThat(updatedCountry.getHeadOfState()).isEqualTo("Mark Rutte");


    }

    @Test
    void getCountriesWithPaginationTest(){

        TreeMap<String, Object> params = new TreeMap<>();
        params.put("search", "ea");
        params.put("continent", "Asia");
        params.put("region", "Eastern Asia");
        params.put("pageNo", "0");

        Optional<List<Country>> result = Optional.ofNullable(countryServiceImpl.getCountries(params));

        log.info("Query result --> {}", result.get().size());
        log.info("Query result --> {}", result.get().toString());

        assertThat(result.get().size()).isGreaterThan(0);



    }

    @Test
    void getCountriesWithNullParamValuesTest(){

        TreeMap<String, Object> params = new TreeMap<>();
        params.put("search", null);
        params.put("continent", null);
        params.put("region", null);
        params.put("pageNo", null);

        Optional<List<Country>> result = Optional.ofNullable(countryServiceImpl.getCountries(params));

        log.info("Query result --> {}", result.get().size());
        log.info("Query result --> {}", result.get().toString());

        assertThat(result.get().size()).isGreaterThan(0);



    }

}