import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Blog implements DatabaseManagement {
  private String name;
  private String content;
  private Timestamp created;
  private int id;

  private static final int MAX_TAG_NUMBER = 3;

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

  public void addTag(Tag tag) {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "insert into blog_tag (blog_id, tag_id) values (:blog_id, :tag_id);";
      con.createQuery(joinQuery)
      .addParameter("blog_id", this.getId())
      .addParameter("tag_id", tag.getId())
      .executeUpdate();
    }
  }

  public List<Tag> getTagsByBlog() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT tags.* FROM blogs JOIN blog_tag ON (blogs.id = blog_tag.blog_id) JOIN tags ON (blog_tag.tag_id = tags.id) WHERE blogs.id = :blog_id;";
      return con.createQuery(sql)
      .addParameter("blog_id", this.id)
      .executeAndFetch(Tag.class);
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
