package com.worldgdp.client.api;

import com.worldgdp.models.CountryLanguage;
import com.worldgdp.repository.CountryLanguageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("worldgdp/api/languages")
@Slf4j
public class CountryLanguageAPIController {

    @Autowired
    CountryLanguageRepository cLanguageDao;

    private static int PAGE_SIZE = 20;

    @GetMapping("/{countryCode}")
    public ResponseEntity<?> getLanguages(@PathVariable String countryCode,
                                          @RequestParam(name="pageNo", defaultValue="1") Integer pageNo){
        try {
            Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE);
            return ResponseEntity.ok(cLanguageDao.findByCountrycode(countryCode, pageable));
        }catch(Exception ex) {
            System.out.println("Error while getting languages for country: {}"+
                    countryCode+ ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while languages cities");
        }

    }

//    @PostMapping("/{countryCode}")
//    public ResponseEntity<?> addLanguage(@PathVariable String countryCode,
//                                         @Valid @RequestBody CountryLanguage language){
//        try {
//            if ( cLanguageDao.(countryCode, language.getLanguage())) {
//                return ResponseEntity.badRequest()
//                        .body("Language already exists for country");
//            }
//            cLanguageDao.save(language);
//            return ResponseEntity.ok(language);
//        }catch(Exception ex) {
//            System.out.println("Error while adding language: {} to country: {}"+
//                    language+ countryCode+ ex);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while adding language");
//        }
//    }

    @DeleteMapping("/{countryCode}/language/{language}")
    public ResponseEntity<?> deleteLanguage(@PathVariable String countryCode,
                                            @PathVariable String language){
//        try {
//
//
//            cLanguageDao.delete(countryCode, language);;
//            return ResponseEntity.ok().build();
//        }catch(Exception ex) {
//            System.out.println("Error occurred while deleting language : {}, for country: {}"+
//                    language+ countryCode+ ex);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error occurred while deleting the language");
//        }
        return null;
    }

}
