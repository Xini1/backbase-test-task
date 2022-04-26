Execute run_local.sh.
It basically eliminates some manual steps in using "docker compose".
Instead, you can use "docker compose" directly: there are Dockerfile and docker-compose.yml provided.

## Example requests

Search for film by its title

``` bash
curl -X GET -H "apiToken: 29075f13" http://localhost:8080/films?search=avengers 
```

Rate film by its IMDB ID

``` bash
curl -X POST -H "apiToken: 29075f13" -H "Content-Type: application/json" http://localhost:8080/films/tt0848228/ratings -d "10"
```

List top-rated films sorted by box office

``` bash
curl -X GET -H "apiToken: 29075f13" http://localhost:8080/films/top
```