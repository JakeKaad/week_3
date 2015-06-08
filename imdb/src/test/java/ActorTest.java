import org.junit.*;
import static org.junit.Assert.*;

public class ActorTest {

  @Rule
  public DataBaseRule database = new DataBaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Actor.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Actor firstActor = new Actor("Mow the lawn");
    Actor secondActor = new Actor("Mow the lawn");
    assertTrue(firstActor.equals(secondActor));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Actor myActor = new Actor("Mow the lawn");
    myActor.save();
    Actor savedActor = Actor.all().get(0);
    assertTrue(savedActor.equals(myActor));
  }

  @Test
  public void save_assignsIdToObject() {
    Actor myActor = new Actor("Mow the lawn");
    myActor.save();
    Actor savedActor = Actor.all().get(0);
    assertEquals(myActor.getId(), savedActor.getId());
  }

  @Test
  public void find_findsActorInDatabase_true() {
    Actor myActor = new Actor("Mow the lawn");
    myActor.save();
    Actor savedActor = Actor.find(myActor.getId());
    assertTrue(myActor.equals(savedActor));
  }

  @Test
  public void update_allowsAssociationToAMovie() {
    Movie myMovie = new Movie("Hackers");
    myMovie.save();
    Actor myActor = new Actor("Angelina Jolie");
    myActor.save();
    myActor.update(myMovie.getId());
    assertTrue(myActor.movies().get(0).equals(myMovie));
  }
}
