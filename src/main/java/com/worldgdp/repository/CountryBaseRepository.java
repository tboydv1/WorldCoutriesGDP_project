package com.worldgdp.repository;

import com.worldgdp.models.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@NoRepositoryBean
public interface CountryBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findByName(String name);

    List<T> findByContinent(String continent, Pageable pageable);

    T findByCode(String code);

    /**
     *
     * @param search
     * @param pageable
     * @return
     */
    @Query(value = "select c from Country c where lower(c.name) like %:search%")
    Optional<List<T>> findBySearch(@Param("search") String search, Pageable pageable);

    /**
     * Query values from db, ignoring null values
     * @param search
     * @param continent
     * @param region
     * @return
     */
    @Query("select c " +
            "from Country c " +
            "where (lower(c.name) like %:search% or :search is null) " +
            "and (c.continent = :continent or :continent is null)" +
            "and (c.region = :region or :region is null)")
    Optional<List<T>> findCountries(String search, String continent, String region, Pageable pageable);



    @Query("select distinct c.continent as ct from Country c order by ct")
    Optional<List<String>> getContinents();

    @Query("select distinct c.region as r from Country c order by r")
    Optional<List<String>> getRegions();

    @Query("select count(c) " +
            "from Country c " +
            "where (lower(c.name) like %:search% or :search is null) " +
            "and (c.continent = :continent or :continent is null)" +
            "and (c.region = :region or :region is null)")
    int getCountriesCount(String search, String continent, String region);

    @Query("select count(c) from Country c")
    int getCountriesCount();

    @Query("select distinct c.governmentForm as g from Country c order by g")
    Optional<List<String>> getGovernmentTypes();

    @Query("select distinct c.headOfState as h from Country c order by h")
    Optional<List<String>> getHeadOfStates();

}