There are three bottlenecks: OMDB API HTTP requests, CSV file reading, SQL statement to report 10 top-rated films.

Calls to OMDB API (it is a static data, basically) can be easily cached. We can even save fetched data to local database
for future use instead of making HTTP requests.

Reading file is faster than fetching data from database, but it still can be improved. We can read it upfront, storing
relevant information in memory (if it fits without harm) for fast access.

Regarding top-rated films report, it can be made eventual consistent by introducing read replica. Such replica will be
updated by a background process once in an hour (or 10 minutes, for example), stored in different table.

Also whole application can be scaled horizontally: it is stateless.