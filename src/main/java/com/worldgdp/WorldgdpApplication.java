package com.worldgdp;

import com.worldgdp.repository.CountryBaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.Entity;

@EntityScan("com.worldgdp.models")
@EnableJpaRepositories(basePackages = "com.worldgdp.*")
@SpringBootApplication
public class WorldgdpApplication {

	public static void main(String[] args) {

		SpringApplication.run(WorldgdpApplication.class, args);
	}

}
