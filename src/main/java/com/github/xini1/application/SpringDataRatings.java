package com.github.xini1.application;

import com.github.xini1.application.entity.RatingEntity;
import com.github.xini1.port.Ratings;
import org.springframework.data.domain.PageRequest;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class SpringDataRatings implements Ratings {

    private final RatingsRepository ratingsRepository;

    SpringDataRatings(RatingsRepository ratingsRepository) {
        this.ratingsRepository = ratingsRepository;
    }

    @Override
    public void add(String apiToken, String imdbId, int rating) {
        var ratingEntity = new RatingEntity();
        ratingEntity.setHash(hash(apiToken));
        ratingEntity.setImdbId(imdbId);
        ratingEntity.setRating(rating);
        ratingsRepository.save(ratingEntity);
    }

    @Override
    public Page top(int page, int elementsOnPage) {
        return page(ratingsRepository.sortedByRating(PageRequest.of(page, elementsOnPage)));
    }

    @Override
    public int average(String imdbId) {
        return ratingsRepository.average(imdbId)
                .map(Math::round)
                .orElse(0);
    }

    private byte[] hash(String apiToken) {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(
                            new PBEKeySpec(
                                    apiToken.toCharArray(),
                                    "salt".getBytes(StandardCharsets.UTF_8), //to reproduce hash without storing salt
                                    65536,
                                    128
                            )
                    )
                    .getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new CouldNotHashApiToken(e);
        }
    }

    private Page page(org.springframework.data.domain.Page<RatingsRepository.TopRatedProjection> page) {
        return new Page() {
            @Override
            public Map<String, Integer> imdbIdToRating() {
                return page.stream()
                        .collect(toMap());
            }

            @Override
            public int page() {
                return page.getNumber();
            }

            @Override
            public int totalPages() {
                return page.getTotalPages();
            }
        };
    }

    private Collector<RatingsRepository.TopRatedProjection, ?, Map<String, Integer>> toMap() {
        return Collectors.toMap(
                RatingsRepository.TopRatedProjection::getImdbId,
                topRatedProjection -> Math.round(topRatedProjection.getRating())
        );
    }

    public static final class CouldNotHashApiToken extends RuntimeException {

        public CouldNotHashApiToken(Throwable cause) {
            super(cause);
        }
    }
}
