package com.worldgdp.client;

import com.worldgdp.models.Country;
import com.worldgdp.models.dto.CountryDto;
import com.worldgdp.service.CountryService;
import com.worldgdp.service.WorlBankApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/countries")
@Slf4j
public class CountryAPIController {

    @Autowired
    CountryService countryService;

    @Autowired
    WorlBankApiClient worlBankApiClient;

    @GetMapping
    public ResponseEntity<?> getCountries(@RequestParam(name = "search", required = false) String searchTerm,
                                          @RequestParam(name = "continent", required = false) String continent,
                                          @RequestParam(name = "region", required = false) String region,
                                          @RequestParam(name = "pageNo", required = false) Integer pageNo){

        Map<String, Object> params = new TreeMap<>();
        params.put("search", searchTerm);
        params.put("continent", continent);
        params.put("region", region);
        if(pageNo != null)
            params.put("pageNo", pageNo.toString());

        List<Country> countryList = countryService.getCountries(params);

        Map<String, Object> response = new HashMap<>();
        response.put("list", countryList);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/{countryCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editCountry(@PathVariable String countryCode, @RequestBody CountryDto country){

        //logic
        Country countryFromDb = null;
        try {
           countryFromDb = countryService.editCountry(countryCode, country);
        }catch(Exception ex) {
            System.out.println("Error while editing the country: {} with data: {}"+ countryCode+ country+ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while ediiting the country");
        }
        return ResponseEntity.ok(countryFromDb);

    }

    @GetMapping("/{countryCode}/gdp")
    public ResponseEntity<?> getGDP(@PathVariable String countryCode){

        //logic
        try {
            return ResponseEntity.ok(worlBankApiClient.getGDP(countryCode));
        }catch(Exception ex) {
            System.out.println("Error while getting GDP for country: {}"+ countryCode+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while getting the GDP");
        }

    }




}
