* Add logging
* Add proper exception handling on the level of Spring Framework (goal is to be more descriptive): domain level
  exceptions are being translated only to status codes
* Add schema migration tool, like Liquibase: currently there is only one simple table created with SQL script. If we
  expect the schema to change, integrating Liquibase could be viable choice.
* Divide application to more explicit modules with Jigsaw. It potentially can enforce even stronger encapsulation. In my
  vision, there could be modules: core domain, ports (domain API), OMDB API integration, database integration, Spring
  Framework with HTTP controllers integration, CSV file, facade component (builds up whole application from other
  modules).
* Add Javadocs to at least interfaces. This will help readers understand intentions and API of the system.
* Caching would help, because film descriptions and Oscar winners are static, and making HTTP calls to OMDB API or
  reading file every time is expensive. In the case of reading file, we could even load all relevant information from it
  in memory (if it's not too big).