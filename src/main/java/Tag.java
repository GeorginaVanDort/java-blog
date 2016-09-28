import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Tag implements DatabaseManagement {
  private String name;
  private int id;

  public Tag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  @Override
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "delete from tags where id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    } else {
      Tag newTag = (Tag) otherTag;
      return this.getName().equals(newTag.getName());
    }
  }

  public static List<Tag> all() {
    String sql = "select * from tags";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Tag.class);
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id=:id";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  @Override
  public void update(String updatedName) {
    try (Connection con = DB.sql2o.open()){
      this.name = updatedName;
      String sql = "update tags set name = :name where id=:id";
      con.createQuery(sql)
      .addParameter("name", this.name)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }


}
