package com.github.xini1.domain;

import com.github.xini1.port.Ratings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
final class InMemoryRatings implements Ratings {

    private final Map<String, List<Rating>> map = new HashMap<>();

    @Override
    public void add(String apiToken, String imdbId, int rating) {
        map.computeIfAbsent(imdbId, unused -> new ArrayList<>());
        map.get(imdbId).add(new Rating(apiToken, rating));
    }

    @Override
    public Page top(int page, int elementsOnPage) {
        return page(imdbIdToAverageRating(page), page, elementsOnPage);
    }

    @Override
    public int average(String imdbId) {
        if (!map.containsKey(imdbId)) {
            return 0;
        }

        return Math.round(average(map.get(imdbId)));
    }

    private Page page(Map<String, Integer> imdbIdToAverageRating, int page, int elementsOnPage) {
        return new Page() {
            @Override
            public Map<String, Integer> imdbIdToRating() {
                return imdbIdToAverageRating.entrySet()
                        .stream()
                        .skip((long) elementsOnPage * page)
                        .limit(elementsOnPage)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }

            @Override
            public int page() {
                return page;
            }

            @Override
            public int totalPages() {
                return imdbIdToAverageRating.size() / elementsOnPage + accountForLastPage();
            }

            private int accountForLastPage() {
                if (imdbIdToAverageRating.size() % elementsOnPage == 0) {
                    return 0;
                }

                return 1;
            }
        };
    }

    private Map<String, Integer> imdbIdToAverageRating(int page) {
        return map.entrySet()
                .stream()
                .map(this::imdbIdToAverageRating)
                .sorted(byAverageRating())
                .map(this::imdbIdToRoundedAverageRating)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map.Entry<String, Integer> imdbIdToRoundedAverageRating(Map.Entry<String, Float> entry) {
        return Map.entry(entry.getKey(), Math.round(entry.getValue()));
    }

    private Map.Entry<String, Float> imdbIdToAverageRating(Map.Entry<String, List<Rating>> entry) {
        return Map.entry(entry.getKey(), average(entry.getValue()));
    }

    private Comparator<Map.Entry<String, Float>> byAverageRating() {
        return Map.Entry.<String, Float>comparingByValue().reversed();
    }

    private float average(List<Rating> ratings) {
        return sum(ratings) / (float) ratings.size();
    }

    private int sum(List<Rating> ratings) {
        return ratings.stream()
                .mapToInt(rating -> rating.value)
                .sum();
    }

    private static final class Rating {

        private final String apiToken;
        private final int value;


        private Rating(String apiToken, int value) {
            this.apiToken = apiToken;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(apiToken, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Rating rating = (Rating) o;
            return value == rating.value && apiToken.equals(rating.apiToken);
        }
    }
}
