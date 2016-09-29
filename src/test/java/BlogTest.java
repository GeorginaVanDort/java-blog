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
  Blog blog3 = new Blog("ThatFood", "It was okay.");
  Blog blog4 = new Blog("NotFood", "ugh!");

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

  @Test
  public void getTagsByBlog_returnsAllInstancesOfBlog_true() {
    Blog firstBlog = blog;
    firstBlog.save();
    Blog secondBlog = blog2;
    secondBlog.save();
    Tag tag1 = new Tag("Cooking");
    tag1.save();
    Tag tag2 = new Tag("Cleaning");
    tag2.save();
    Tag tag3 = new Tag("Swordplay");
    tag3.save();
    Tag tag4 = new Tag("Reading");
    tag4.save();
    firstBlog.addTag(tag1);
    firstBlog.addTag(tag2);
    firstBlog.addTag(tag3);
    secondBlog.addTag(tag1);
    List savedTags = firstBlog.getTagsByBlog();
    assertEquals(3, savedTags.size());
  }

  @Test
  public void getBlogsByTags_returnsAllInstancesOfBlog_true() {
    Blog firstBlog = blog;
    firstBlog.save();
    Blog secondBlog = blog2;
    secondBlog.save();
    Blog thirdBlog = blog3;
    thirdBlog.save();
    Blog fourthBlog = blog4;
    fourthBlog.save();
    Tag tag1 = new Tag("Cleaning");
    tag1.save();
    firstBlog.addTag(tag1);
    secondBlog.addTag(tag1);
    thirdBlog.addTag(tag1);
    fourthBlog.addTag(tag1);
    List savedBlogs = tag1.getBlogsByTag();
    assertEquals(4, savedBlogs.size());
  }


}
