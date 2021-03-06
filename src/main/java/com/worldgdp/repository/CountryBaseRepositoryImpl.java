package com.worldgdp.repository;

import com.worldgdp.dao.mapper.CountryRowMapper;
import com.worldgdp.models.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CountryBaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID > implements CountryBaseRepository<T, ID  > {

    private final EntityManager entityManager;


    public CountryBaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<T> findByName(String name) { return null; }

    @Override
    public Optional<List<String>> getContinents() {
        return Optional.empty();
    }

    @Override
    public Optional<List<String>> getRegions() {
        return Optional.empty();
    }

    @Override
    public int getCountriesCount(String search, String continent, String region) {
        return 0;
    }

    @Override
    public int getCountriesCount() {
        return 0;
    }

    @Override
    public Optional<List<String>> getGovernmentTypes() {
        return Optional.empty();
    }

    @Override
    public Optional<List<String>> getHeadOfStates() {
        return null;
    }

    @Override
    public List<T> findByContinent(String continent, Pageable pageable) {
        return null;
    }


    @Override
    public T findByCode(String code) {
        return null;
    }

    @Override
    public Optional<List<T>> findBySearch(String search, Pageable pageable) {
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> findCountries(String search, String continent, String region, Pageable pageable) {
        return Optional.empty();
    }


}
