import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Blog implements DatabaseManagement {
  private String name;
  private String content;
  private Timestamp created;
  private int id;

  public Blog(String name, String content) {
    this.name = name;
    this.content = content;
  }

  public String getName() {
    return name;
  }

  public String getContent() {
    return content;
  }

  public Timestamp getCreated() {
    return created;
  }

  public int getId() {
    return id;
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO blogs (name, content, created) VALUES (:name, :content, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("content", this.content)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "delete from blogs where id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public void update(String updatedContent) {
    try (Connection con = DB.sql2o.open()){
      this.content = updatedContent;
      String sql = "update blogs set content = :content where id=:id";
      con.createQuery(sql)
      .addParameter("content", this.content)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherBlog) {
    if (!(otherBlog instanceof Blog)) {
      return false;
    } else {
      Blog newBlog = (Blog) otherBlog;
      return this.getName().equals(newBlog.getName()) &&
             this.getContent().equals(newBlog.getContent());
    }
  }

  public static List<Blog> all() {
    String sql = "select * from blogs";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Blog.class);
    }
  }

  public static Blog find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM blogs WHERE id=:id";
      Blog blog = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Blog.class);
      return blog;
    }
  }


}
