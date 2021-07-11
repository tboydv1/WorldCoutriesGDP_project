package com.worldgdp.client.api;

import com.worldgdp.models.City;
import com.worldgdp.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/worldgdp/api/cities")
public class CityAPIController {


    @Autowired
    CityRepository cityDao;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getCities(@PathVariable String countryCode,
                                       @RequestParam(name="pageNo", defaultValue="1") Integer pageNo){
        try {

            Pageable pageable = PageRequest.of(pageNo, 20);
            return new ResponseEntity<>(cityDao.findByCountryCode(countryCode, pageable),
                    HttpStatus.OK);
        }catch(Exception ex) {
            System.out.println("Error while getting cities for country: {}"+
                    countryCode+ ex);
            return new ResponseEntity<>("Error while getting cities",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{countryCode}")
    public ResponseEntity<?> addCity(@PathVariable String countryCode,
                                     @Valid @RequestBody City city){
        try {
            city.setCountryCode(countryCode);
            cityDao.save(city);
            return new ResponseEntity<>(city, HttpStatus.CREATED);
        }catch(Exception ex) {
            System.out.println("Error while adding city: {} to country: {}"+
                    city+ countryCode+ ex);
            return new ResponseEntity<>("Error while adding city",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId){
        try {
            cityDao.deleteById(cityId);
            return ResponseEntity.ok().build();
        }catch(Exception ex) {
            System.out.println("Error occurred while deleting city : {}"+ cityId+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the city: " + cityId);
        }
    }
}
