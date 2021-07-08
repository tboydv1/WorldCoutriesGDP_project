package com.worldgdp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class    Country {

    @Id
    @NotNull @Size(max = 3, min = 3)
    private String code;
    @NotNull @Size(max =52)
    private String name;
    @NotNull
    private String continent;
    @NotNull @Size(max = 26) private String region;
    @NotNull
    @Column(name="surfacearea")
    private Double surfaceArea;
    @Column(name = "indepyear")
    private Short indepYear;
    @NotNull
    private Long population;
    @Column(name = "lifeexpectancy")
    private Double lifeExpectancy;
    private Double gnp;
    private Double gnpold;
    @NotNull
    @Column(name = "localname" )
    private String localName;
    @NotNull
    @Column(name = "governmentform" )
    private String governmentForm;
    @Column(name = "headofstate" )
    private String headOfState;

    @OneToOne()
    @JoinColumn(name = "capital")
    private City capital;
    @NotNull  private String code2;
}
