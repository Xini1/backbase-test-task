CREATE TABLE ratings(
    api_token varchar NOT NULL,
    imdb_id varchar NOT NULL,
    rating int NOT NULL,
    PRIMARY KEY(api_token,imdb_id)
);