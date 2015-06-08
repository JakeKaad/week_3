import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DataBaseRule database = new DataBaseRule();

  @Test
  public void indexIsDisplayed() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Welcome to the Movie Database!");
  }

  @Test
  public void actorsDisplayedOnIndex() {
    Actor testActor = new Actor("Angelina Jolie");
    testActor.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Angelina Jolie");
  }

  @Test
  public void moviesDisplayedOnIndex() {
    Movie testMovie = new Movie("Hackers");
    testMovie.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hackers");
  }
  //
  @Test
  public void actorsDisplayedOnActorsPage() {
    Actor testActor = new Actor("Angelina Jolie");
    testActor.save();
    goTo("http://localhost:4567/");
    click("a", withText("Add a new actor"));
    assertThat(pageSource()).contains("Angelina Jolie");
  }

  @Test
  public void actorsFormDisplayedOnActorsPage() {
    goTo("http://localhost:4567/actors");
    assertThat(pageSource()).contains("Add actor");
  }
  //
  @Test
  public void moviesDisplayedOnMoviesPage() {
    Movie testMovie = new Movie("Hackers");
    testMovie.save();
    goTo("http://localhost:4567/");
    click("a", withText("Add a new movie"));
    assertThat(pageSource()).contains("Hackers");
  }

  @Test
  public void moviesFormDisplayedOnMoviesPage() {
    goTo("http://localhost:4567/movies");
    assertThat(pageSource()).contains("Add movie");
  }
  //
  @Test
  public void actorsCanBeAddedByUsers() {
    goTo("http://localhost:4567/actors");
    fill("#name").with("Angelina Jolie");
    submit(".btn");
    assertThat(pageSource()).contains("Angelina Jolie");
    assertThat(pageSource()).contains("There are no movies yet!");
  }

  @Test
  public void moviesCanBeAddedByUsers() {
    goTo("http://localhost:4567/movies");
    fill("#name").with("Hackers");
    submit(".btn");
    assertThat(pageSource()).contains("Hackers");
    assertThat(pageSource()).contains("There are no actors yet!");
  }
  //
  @Test
  public void actorsCanBeAddedToMovies() {
    Movie testMovie = new Movie("Hackers");
    testMovie.save();
    Actor testActor = new Actor("Angelina Jolie");
    testActor.save();
    String moviePath = String.format("http://localhost:4567/movies/%d", testMovie.getId());
    goTo(moviePath);
    String actorCheckBox = String.format("#actor-%d", testActor.getId());
    find(actorCheckBox).click();
    submit(".btn");
    assertThat(pageSource()).contains("Here are all the actors in this movie:");
  }
}
