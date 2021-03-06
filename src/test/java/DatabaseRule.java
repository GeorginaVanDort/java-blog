import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/blog_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBlogsQuery = "DELETE FROM blogs *;";
      String deleteCommentsQuery = "DELETE FROM comments *;";
      String deleteTagsQuery = "DELETE FROM tags *;";
      String deleteJoinsQuery = "DELETE FROM blog_tag *;";
      String deleteUserQuery = "DELETE FROM users *;";
      con.createQuery(deleteBlogsQuery).executeUpdate();
      con.createQuery(deleteCommentsQuery).executeUpdate();
      con.createQuery(deleteTagsQuery).executeUpdate();
      con.createQuery(deleteJoinsQuery).executeUpdate();
      con.createQuery(deleteUserQuery).executeUpdate();
    }
  }

}
