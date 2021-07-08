package com.worldgdp.models;

import lombok.Data;
import lombok.Generated;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
public class City implements Serializable {
    @NotNull @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull @Size(max = 35) private String name;
    @NotNull @Size(max = 3, min = 3)
    @Column(name = "countrycode")
    private String countryCode;
    @OneToOne
    private Country country;
    @NotNull @Size(max = 200)
    private String district;
    private Long population;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", countryCode='").append(countryCode).append('\'');
        sb.append(", country=").append(country);
        sb.append(", district='").append(district).append('\'');
        sb.append(", population=").append(population);
        sb.append('}');
        return sb.toString();
    }
}
