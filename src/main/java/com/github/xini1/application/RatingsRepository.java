package com.github.xini1.application;

import com.github.xini1.application.entity.RatingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Tereshchenko
 */
interface RatingsRepository extends CrudRepository<RatingEntity, RatingEntity.CompositeKey> {

    @Query(
            "SELECT e.imdbId as imdbId, AVG(e.rating) as rating FROM RatingEntity e GROUP BY e.imdbId " +
                    "ORDER BY AVG(e.rating) DESC"
    )
    List<TopRatedProjection> sortedByRating(Pageable pageable);

    @Query("SELECT AVG(e.rating) FROM RatingEntity e GROUP BY e.imdbId")
    Optional<Float> average(String imdbId);

    interface TopRatedProjection {

        String getImdbId();

        float getRating();
    }
}
