package com.github.xini1.application.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
@Entity
@Table(name = "ratings")
@IdClass(RatingEntity.CompositeKey.class)
public class RatingEntity {

    @Id
    private String apiToken;
    @Id
    private String imdbId;
    private int rating;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public static class CompositeKey implements Serializable {

        private static final long serialVersionUID = -4802684950936109034L;

        private String apiToken;
        private String imdbId;

        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
        }

        public String getImdbId() {
            return imdbId;
        }

        public void setImdbId(String imdbId) {
            this.imdbId = imdbId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(apiToken, imdbId);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            var that = (CompositeKey) o;
            return apiToken.equals(that.apiToken) && imdbId.equals(that.imdbId);
        }
    }
}
