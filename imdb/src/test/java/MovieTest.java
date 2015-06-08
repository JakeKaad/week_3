import org.junit.*;
import static org.junit.Assert.*;

public class MovieTest {

  @Rule
  public DataBaseRule database = new DataBaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Movie.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Movie firstMovie = new Movie("Mow the lawn");
    Movie secondMovie = new Movie("Mow the lawn");
    assertTrue(firstMovie.equals(secondMovie));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Movie myMovie = new Movie("Mow the lawn");
    myMovie.save();
    Movie savedMovie = Movie.all().get(0);
    assertTrue(savedMovie.equals(myMovie));
  }

  @Test
  public void save_assignsIdToObject() {
    Movie myMovie = new Movie("Mow the lawn");
    myMovie.save();
    Movie savedMovie = Movie.all().get(0);
    assertEquals(myMovie.getId(), savedMovie.getId());
  }

  @Test
  public void find_findsMovieInDatabase_true() {
    Movie myMovie = new Movie("Mow the lawn");
    myMovie.save();
    Movie savedMovie = Movie.find(myMovie.getId());
    assertTrue(myMovie.equals(savedMovie));
  }

  @Test
  public void update_allowsAssociationToAMovie() {
    Movie myMovie = new Movie("Hackers");
    myMovie.save();
    Actor myActor = new Actor("Angelina Jolie");
    myActor.save();
    myMovie.update(myActor.getId());
    assertTrue(myMovie.actors().get(0).equals(myActor));
  }

}
