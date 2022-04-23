package com.github.xini1.application;

import com.github.xini1.application.entity.RatingEntity;
import com.github.xini1.domain.FilmsModuleConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Maxim Tereshchenko
 */
@Configuration
@EnableFeignClients(basePackageClasses = OmdbApiFeignClient.class)
@EnableJpaRepositories(basePackageClasses = RatingsRepository.class)
@EntityScan(basePackageClasses = RatingEntity.class)
public class SpringConfiguration {

    @Bean
    ApplicationController applicationController(
            OmdbApiFeignClient omdbApiFeignClient,
            RatingsRepository ratingsRepository
    ) {
        var configuration = new FilmsModuleConfiguration(
                new OmdbApiFilmDescriptions(omdbApiFeignClient),
                new OscarWinnersFromFile("academy_awards.csv"),
                new SpringDataRatings(ratingsRepository)
        );

        return new ApplicationController(
                configuration.searchFilmUseCase(),
                configuration.rateFilmUseCase(),
                configuration.list10TopRatedFilmsUseCase()
        );
    }
}
