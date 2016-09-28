import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class TagTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  Tag tag = new Tag("tag1");
  Tag tag2 = new Tag("tag2");

  @Test
  public void tag_instantiatesCorrectly_true() {
    Tag testTag = tag;
    assertEquals(true, testTag instanceof Tag);
  }

  @Test
  public void getName_tagInstantiatesWithName_true() {
    Tag testTag = tag;
    assertEquals("tag1", testTag.getName());
  }

  @Test
  public void equals_returnsTrueIfNameAndContentAreSame_true() {
    Tag testTag = tag;
    Tag anotherTag = tag;
    assertTrue(testTag.equals(anotherTag));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Tag() {
    Tag testTag = tag;
    testTag.save();
    assertEquals(true, Tag.all().get(0).equals(testTag));
  }

  @Test
  public void save_assignsIdToTag() {
    Tag testTag = tag;
    testTag.save();
    Tag savedTag = Tag.all().get(0);
    assertEquals(savedTag.getId(), testTag.getId());
  }

  @Test
  public void all_returnsAllInstancesOfTag_true() {
    Tag firstTag = tag;
    firstTag.save();
    Tag secondTag = tag2;
    secondTag.save();
    assertEquals(true, Tag.all().get(0).equals(firstTag));
    assertEquals(true, Tag.all().get(1).equals(secondTag));
  }

  @Test
  public void find_returnsTagWithSameId_secondTag() {
    Tag firstTag = tag;
    firstTag.save();
    Tag secondTag = tag2;
    secondTag.save();
    assertEquals(Tag.find(secondTag.getId()), secondTag);
  }


  @Test
  public void delete_deletesTag_true(){
    Tag testTag = tag;
    testTag.save();
    testTag.delete();
    assertEquals(null, Tag.find(testTag.getId()));
  }

  @Test
  public void edit_editTagReturnsUpdatedContent_True(){
    Tag testTag = tag;
    testTag.save();
    testTag.update("tageeee");
    assertEquals("tageeee", testTag.getName());
  }

}
