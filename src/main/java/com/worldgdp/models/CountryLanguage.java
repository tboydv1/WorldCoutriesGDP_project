package com.worldgdp.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "countrylanguage")
@IdClass(CountryLanguageId.class)
public class CountryLanguage implements Serializable {


    @NotNull @Id
    private String countrycode;

    @Id
    @NotNull @Size(max = 30)
    private String language;

    @NotNull
    private boolean isofficial;

    @NotNull
    private Double percentage;

}
