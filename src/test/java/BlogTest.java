import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;

public class BlogTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  Blog blog = new Blog("Food", "I love food!");
  Blog blog2 = new Blog("OtherFood", "Other food I love!");

  @Test
  public void blog_instantiatesCorrectly_true() {
    Blog testBlog = blog;
    assertEquals(true, testBlog instanceof Blog);
  }

  @Test
  public void getName_blogInstantiatesWithName_true() {
    Blog testBlog = blog;
    assertEquals("Food", testBlog.getName());
  }

  @Test
  public void getContent_blogInstantiatesWithName_true() {
    Blog testBlog = blog;
    assertEquals("I love food!", testBlog.getContent());
  }

  @Test
  public void equals_returnsTrueIfNameAndContentAreSame_true() {
    Blog testBlog = blog;
    Blog anotherBlog = blog;
    assertTrue(testBlog.equals(anotherBlog));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Blog() {
    Blog testBlog = blog;
    testBlog.save();
    assertEquals(true, Blog.all().get(0).equals(testBlog));
  }

  @Test
  public void save_assignsIdToBlog() {
    Blog testBlog = blog;
    testBlog.save();
    Blog savedBlog = Blog.all().get(0);
    assertEquals(savedBlog.getId(), testBlog.getId());
  }

  @Test
  public void all_returnsAllInstancesOfBlog_true() {
    Blog firstBlog = blog;
    firstBlog.save();
    Blog secondBlog = blog2;
    secondBlog.save();
    assertEquals(true, Blog.all().get(0).equals(firstBlog));
    assertEquals(true, Blog.all().get(1).equals(secondBlog));
  }

  @Test
  public void find_returnsBlogWithSameId_secondBlog() {
    Blog firstBlog = blog;
    firstBlog.save();
    Blog secondBlog = blog2;
    secondBlog.save();
    assertEquals(Blog.find(secondBlog.getId()), secondBlog);
  }

  @Test
  public void save_recordsTimeOfCreationInDatabase() {
    Blog testBlog = blog;
    testBlog.save();
    Timestamp savedBlogBirthday = Blog.find(testBlog.getId()).getCreated();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(rightNow.getDay(), savedBlogBirthday.getDay());
  }

  @Test
  public void delete_deletesBlog_true(){
    Blog testBlog = blog;
    testBlog.save();
    testBlog.delete();
    assertEquals(null, Blog.find(testBlog.getId()));
  }

  @Test
  public void edit_editBlogReturnsUpdatedContent_True(){
    Blog testBlog = blog;
    testBlog.save();
    testBlog.update("This is the new stuff about food");
    assertEquals("This is the new stuff about food", testBlog.getContent());
  }

}
