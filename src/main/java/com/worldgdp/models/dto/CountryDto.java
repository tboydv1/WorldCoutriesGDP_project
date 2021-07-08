package com.worldgdp.models.dto;

import com.worldgdp.models.City;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CountryDto {


    private String name;
    private String continent;
    private Double surfaceArea;
    private Short indepYear;
    private Long population;
    private Double lifeExpectancy;
    private Double gnp;
    private Double gnpold;
    private String localName;
    private String governmentForm;
    private String headOfState;
    private City capital;
    @NotNull
    private String code2;
}
