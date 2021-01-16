
### **Technical Specification**

| URI | Method | Status| Description |
|-----|--------|-------|-------------|
|/gmdb/movies|GET|200|Get list of all movies with details|

### **Sample Request and Response**

GET /gmdb/movies
````
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
