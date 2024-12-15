package com.tfg.eldest.periodo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.JUNE;
import static java.time.Month.SEPTEMBER;

@Configuration
public class PeriodoConfig {
    @Bean
    @Order(2)
    CommandLineRunner commandLineRunnerPeriodo(PeriodoRepository repository) {
        return args -> {
            List<Periodo> periodos = new ArrayList<>();



            // Creamos 20 periodos
            for (long i = 1; i <= 5; i++) {
                int year = 2002 + (int) i;

                // Creamos el periodo
                Periodo periodo = new Periodo(
                        i,
                        "curso " + year + "/" + (year+1),
                        LocalDate.of(year, SEPTEMBER, ((3+(int) i)%30)+1),
                        LocalDate.of(year+1, JUNE, ((23+(int) i)%30)+1)
                );

                periodos.add(periodo);
            }

            System.out.println(periodos);
            // Guardamos todos los periodos en el repositorio
            repository.saveAll(periodos);
        };
    }
}
