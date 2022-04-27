package com.github.xini1.application.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
@Entity
@Table(name = "ratings")
@IdClass(RatingEntity.CompositeKey.class)
public class RatingEntity {

    @Id
    private byte[] hash;
    @Id
    private String imdbId;
    private int rating;

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

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public static class CompositeKey implements Serializable {

        private static final long serialVersionUID = -4802684950936109034L;

        private byte[] hash;
        private String imdbId;

        public String getImdbId() {
            return imdbId;
        }

        public void setImdbId(String imdbId) {
            this.imdbId = imdbId;
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(imdbId);
            result = 31 * result + Arrays.hashCode(hash);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompositeKey that = (CompositeKey) o;
            return Arrays.equals(hash, that.hash) && imdbId.equals(that.imdbId);
        }

        public byte[] getHash() {
            return hash;
        }

        public void setHash(byte[] hash) {
            this.hash = hash;
        }
    }
}
