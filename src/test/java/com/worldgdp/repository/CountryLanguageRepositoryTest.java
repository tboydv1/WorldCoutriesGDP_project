package com.worldgdp.repository;

import com.worldgdp.models.CountryLanguage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Sql(scripts = "classpath:db/insert-data.sql")
class CountryLanguageRepositoryTest {

    @Autowired
    CountryLanguageRepository countryLanguageRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void findByCountryCode() {
        int pageNo = 1;
        int PAGE_SIZE = 20;
        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE);
        List<CountryLanguage> result = countryLanguageRepository.findByCountrycode("ABW", pageable);
        log.info("Result --> {}", result);
        assertThat(result.size()).isEqualTo(4);
    }
}