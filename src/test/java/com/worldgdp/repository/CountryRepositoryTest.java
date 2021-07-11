package com.worldgdp.repository;

import com.worldgdp.WorldgdpApplication;
import com.worldgdp.dao.mapper.CountryRowMapper;
import com.worldgdp.models.Country;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {WorldgdpApplication.class})
@Slf4j
@Sql(scripts = "classpath:db/insert-data.sql")
class CountryRepositoryTest {

    @Resource
    CountryRepository countryRepository;

    @Autowired
    NamedParameterJdbcTemplate namedParamJdbcTemplate;

    private static final String SELECT_CLAUSE = "SELECT "
            + "  	c.Code, "
            + "		c.Name, "
            + "		c.Continent, "
            + "		c.region, "
            + "		c.SurfaceArea surface_area, "
            + "		c.IndepYear indep_year, "
            + "		c.Population, "
            + "		c.LifeExpectancy life_expectancy, "
            + "		c.GNP, "
            + "		c.LocalName local_name, "
            + "		c.GovernmentForm government_form, "
            + "		c.HeadOfState head_of_state, "
            + "		c.code2 ,"
            + "		c.capital ,"
            + "		cy.name capital_name "
            + " FROM country c"
            + " LEFT OUTER JOIN city cy ON cy.id = c.capital ";

    private static final String SEARCH_WHERE_CLAUSE = " AND ( LOWER(c.name) "
            + "	LIKE CONCAT('%', LOWER(:search), '%') ) ";

    private static final String CONTINENT_WHERE_CLAUSE =
            " AND c.continent = :continent ";

    private static final String REGION_WHERE_CLAUSE =
            " AND c.region = :region ";

    private static final String PAGINATION_CLAUSE = " ORDER BY c.code "
            + "  LIMIT :offset , :size ";

    private static final Integer PAGE_SIZE = 20;


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

        Optional<List<Country>> countriesBySearch = countryRepository.findBySearch("No".toLowerCase(),  firstPage);
        log.info("Size of countries --> {}", countriesBySearch);

        assertFalse(countriesBySearch.isEmpty());
//        assertThat(countriesBySearch.get().get(0).getName().toLowerCase().contains("no"));
        assertThat(countriesBySearch.get().size()).isEqualTo(2);

    }


    @Test
    public void findBySearchTermAndContinentTest(){

        Pageable firstPage = PageRequest.of(0, 20);

        Optional<List<Country>> countriesBySearch = countryRepository.
                findCountries(null, "Asia", "Eastern Asia", firstPage);

        List<Country> savedCountries;

        if(countriesBySearch.isPresent()) {
            savedCountries = countriesBySearch.orElse(null);
            log.info("Size of countries --> {}", savedCountries);
        }

        assertThat(countriesBySearch.get().size()).isEqualTo(8);

    }


    @Test
    public void findByAllRecordsWhenParamsNullTest(){

        Pageable firstPage = PageRequest.of(0, 20);

        Optional<List<Country>> countriesBySearch = countryRepository.
                findCountries(null, null, null, firstPage);

        List<Country> savedCountries;

        if(countriesBySearch.isPresent()) {
            savedCountries = countriesBySearch.orElse(null);
            log.info("Size of countries --> {}", savedCountries);
        }

        assertThat(countriesBySearch.get().size()).isEqualTo(20);

    }






    @Test
    public void getCountriesCountTest(){
        int countriesCount = countryRepository.
                getCountriesCount("ea".toLowerCase(), "Asia", "Eastern Asia");
        log.info("Size of countries --> {}", countriesCount);
        assertThat(countriesCount).isEqualTo(2);

    }

    @Test
    public void getCountriesContinent(){
        Optional<List<String>> continents = countryRepository.
                getContinents();
        log.info("Size of countries --> {}", continents);
        assertThat(continents.isPresent());
        assertThat(continents.get().contains("Asia")).isTrue();
        assertThat(continents.get().size()).isEqualTo(7);
    }

    @Test
    public void getRegions(){
        Optional<List<String>> regions = countryRepository.
                getRegions();
        log.info("Size of countries --> {}", regions);
        assertThat(regions.isPresent()).isTrue();
        assertThat(regions.get().contains("Eastern Asia")).isTrue();
    }

    @Test
    public void getCountries(){
        int pageNo = 1;

        Map<String, Object> params = new HashMap<>();
        params.put("search", null);
        params.put("continent", null);
        params.put("region", "Antarctica");
        Integer offset = (pageNo - 1) * PAGE_SIZE;

        params.put("size", PAGE_SIZE);
        params.put("offset", offset);

        List<Country> result = namedParamJdbcTemplate.query(SELECT_CLAUSE
                        + " WHERE 1 = 1 "
                        + (!StringUtils.isEmpty((String)params.get("search")) ? SEARCH_WHERE_CLAUSE : "")
                        + (!StringUtils.isEmpty((String)params.get("continent")) ? CONTINENT_WHERE_CLAUSE : "")
                        + (!StringUtils.isEmpty((String)params.get("region")) ? REGION_WHERE_CLAUSE : "")
                        + PAGINATION_CLAUSE,
                params, new CountryRowMapper());
        log.info("Result list --> {}", result);
        assertThat(result.size()).isEqualTo(5);
    }




}