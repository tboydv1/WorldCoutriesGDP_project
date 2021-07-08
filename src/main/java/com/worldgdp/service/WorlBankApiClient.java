package com.worldgdp.service;

import com.worldgdp.models.CountryGDP;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.worldgdp.service.URL_Constants.GDP_URL;

@Service
@Slf4j
public class WorlBankApiClient {



    public List<CountryGDP> getGDP(String countryCode) throws ParseException {

        List<CountryGDP> data = new ArrayList<>();

        JSONParser parser = new JSONParser();

        log.info("Enter get gdp method with country code--> {}", countryCode);
        RestTemplate worldBankRestTmplt =  new RestTemplate();

        ResponseEntity<String> response =
                worldBankRestTmplt.getForEntity(String.format(GDP_URL, countryCode), String.class);

        log.info("Response for request --> {}", response);

        //the second element is the actual data and its in an array of objects
        JSONArray responseData = (JSONArray) parser.parse(response.getBody());

        log.info("Response body data --> {}", responseData);

        JSONArray countryDataArr = (JSONArray) responseData.get(1);
        log.info("Response data at index 1 --> {}", countryDataArr);

        JSONObject countryDataYearWise=null;

        for (Object o : countryDataArr) {

            countryDataYearWise = (JSONObject) o;
            String valueStr = "0";
            if (countryDataYearWise.get("value") != null) {
                valueStr = countryDataYearWise.get("value").toString();
                log.info("Value --> {}", valueStr);
            }
            String yearStr = countryDataYearWise.get("date").toString();

            CountryGDP gdp = new CountryGDP();
            gdp.setValue(valueStr != null ? Double.valueOf(valueStr) : null);
            gdp.setYear(Short.valueOf(yearStr));
            data.add(gdp);
        }

        log.info("Data object for request after process --> {}",data);

        return data;
    }
}
