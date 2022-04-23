CREATE TABLE ratings(
    api_token varchar,
    imdb_id varchar,
    rating int,
    PRIMARY KEY(api_token,imdb_id)
);