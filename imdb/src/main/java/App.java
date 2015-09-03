import java.util.HashMap;
import java.util.List;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import spark.QueryParamsMap;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Actor> actors = Actor.all();
      List<Movie> movies = Movie.all();
      model.put("actors", actors);
      model.put("movies", movies);
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/actors", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Actor> actors = Actor.all();
      model.put("actors", actors);
      model.put("template", "templates/actors.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/actors", (request, response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Actor newActor = new Actor(name);
      newActor.save();
      model.put("actor", newActor);
      model.put("movies", Movie.all());
      model.put("template", "templates/actor-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies", (request, response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Movie newMovie = new Movie(name);
      newMovie.save();
      model.put("movie", newMovie);
      model.put("actors", Actor.all());
      model.put("template", "templates/movie-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/actors/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Actor foundActor = Actor.find(Integer.parseInt(request.params("id")));
      model.put("actor", foundActor);
      model.put("movies", Movie.all());
      model.put("template", "templates/actor-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Movie foundMovie = Movie.find(Integer.parseInt(request.params("id")));
      model.put("movie", foundMovie);
      model.put("actors", Actor.all());
      model.put("template", "templates/movie-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/movies", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Movie> movies = Movie.all();
      model.put("movies", movies);
      model.put("template", "templates/movies.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/movies/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Movie foundMovie = Movie.find(Integer.parseInt(request.params("id")));

      QueryParamsMap actorIds = request.queryMap("actor-ids");
        for (String id : actorIds.values()) {
          foundMovie.update(Integer.parseInt(id));
        }

      model.put("movie", foundMovie);
      model.put("actors", Actor.all());
      model.put("template", "templates/movie-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
