package com.worldgdp.client.view;

import com.worldgdp.repository.CityRepository;
import com.worldgdp.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
public class ViewController {

    @Autowired
    CountryService countryServiceImpl;

    @Autowired
    CityRepository cityRepository;

    @GetMapping("/test")
    public String test(Model model){
        log.info("value --> {}", "value");

        model.addAttribute("tvalue", "value");
        return "test";
    }


    @GetMapping({"/countries", "/"})
    public String countries(Model model,
                            @RequestParam Map<String, Object> params
    ) {

        log.info("Request parameters --> {}", params);
        model.addAttribute("continents", countryServiceImpl.getContinents());
        model.addAttribute("regions", countryServiceImpl.getRegions());
        model.addAttribute("countries", countryServiceImpl.getCountries(params));
        model.addAttribute("count", countryServiceImpl.getCountriesCount(params));

        return "countries";
    }

    @GetMapping("/countries/{code}")
    public String countryDetail(@PathVariable String code, Model model) {
        model.addAttribute("c", countryServiceImpl.findByCode(code));
        return "country";
    }

    @GetMapping("/countries/{code}/form")
    public String editCountry(@PathVariable String code, Model model) {
        model.addAttribute("c", countryServiceImpl.findByCode(code));
        model.addAttribute("cities", cityRepository.findByCountryCode(code));
        model.addAttribute("continents", countryServiceImpl.getContinents());
        model.addAttribute("regions", countryServiceImpl.getRegions());
        model.addAttribute("heads", countryServiceImpl.getHeadOfStates());
        model.addAttribute("govs", countryServiceImpl.getGovernmentTypes());
        return "country-form";
    }
}
