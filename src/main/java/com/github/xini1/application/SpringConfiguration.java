package com.github.xini1.application;

import com.github.xini1.domain.FilmsModuleConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maxim Tereshchenko
 */
@Configuration
@EnableFeignClients(basePackageClasses = OmdbApiFeignClient.class)
public class SpringConfiguration {

    @Bean
    ApplicationController applicationController(OmdbApiFeignClient omdbApiFeignClient) {
        var configuration = new FilmsModuleConfiguration(
                new OmdbApiFilmDescriptions(omdbApiFeignClient),
                new OscarWinnersFromFile("academy_awards.csv"),
                null
        );

        return new ApplicationController(
                configuration.searchFilmUseCase(),
                configuration.rateFilmUseCase(),
                configuration.list10TopRatedFilmsUseCase()
        );
    }
}
