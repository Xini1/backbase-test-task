package com.github.xini1.application;

import com.github.xini1.application.entity.RatingEntity;
import com.github.xini1.domain.FilmsModuleConfiguration;
import com.github.xini1.port.FilmDescriptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
            FilmDescriptions filmDescriptions,
            RatingsRepository ratingsRepository,
            @Value("${oscar-winners-file-name}") String fileName
    ) {
        var configuration = new FilmsModuleConfiguration(
                filmDescriptions,
                new OscarWinnersFromFile(fileName),
                new SpringDataRatings(ratingsRepository)
        );

        return new ApplicationController(
                configuration.searchFilmUseCase(),
                configuration.rateFilmUseCase(),
                configuration.list10TopRatedFilmsUseCase()
        );
    }

    @Bean
    @Profile("!test")
    FilmDescriptions filmDescriptions(OmdbApiFeignClient omdbApiFeignClient) {
        return new OmdbApiFilmDescriptions(omdbApiFeignClient);
    }
}
