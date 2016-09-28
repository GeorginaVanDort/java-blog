import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BlogTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  Blog blog = new Blog("Food", "I love food!");

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
}
