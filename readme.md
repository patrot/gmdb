
### **Technical Specification**

| URI | Method | Status| Description |
|-----|--------|-------|-------------|
|/gmdb/movies|GET|200|Get list of all movies with details|
|/gmdb/movies/{title}|GET|200|Get movie based on its title|
|/gmdb/movies/rating/{title}/{rating}|POST|201|Post rating for existing movie|

### **Sample Request and Response**

GET /gmdb/movies
````
Response:
[
  {
    "title": "The Avengers",
    "director": "Joss Whedon",
    "actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
    "release": "2012",
    "description": "Earths mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity",
    "rating": null,
    "id": 1
  },
  {
    "title": "Superman Returns",
    "director": "Bryan Singer",
    "actors": "Brandon Routh, Kate Bosworth, Kevin Spacey, James Marsden",
    "release": "2006",
    "description": "Superman returns to Earth after spending five years in space examining his homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world.",
    "rating": null,
    "id": 2
  }
]
````

GET /gmdb/movies/The Avengers
````
Response:
{
    "title": "The Avengers",
    "director": "Joss Whedon",
    "actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
    "release": "2012",
    "description": "Earths mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity",
    "rating": null,
    "id": 1 
}
````

POST /gmdb/movies/rating/The Avengers/3
````
Response:
{
    "title": "The Avengers",
    "director": "Joss Whedon",
    "actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
    "release": "2012",
    "description": "Earths mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity",
    "rating": "4",
    "userRatings": ["5", "3"],
    "id": 1 
}


