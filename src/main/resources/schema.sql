CREATE TABLE ratings(
    hash bytea NOT NULL,
    imdb_id varchar NOT NULL,
    rating int NOT NULL,
    PRIMARY KEY(hash,imdb_id)
);