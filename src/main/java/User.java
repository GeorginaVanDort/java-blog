import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class User {
  public String name;
  public String email;
  public Timestamp joinDate;
  public boolean banned;
  public boolean admin;
  public int id;

  public User (String name, String email) {
    this.name = name;
    this.email = email;
    banned = false;
    admin = false;
  }

  public String getName(){
    return name;
  }

  public String getEmail(){
    return email;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO user (name, email, joinDate, banned, admin) VALUES (:name, :email, now(), :banned, :admin)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("email", this.email)
        .addParameter("banned", this.banned)
        .addParameter("admin", this.admin)
        .executeUpdate()
        .getKey();
    }
  }
//save() users to DB
//edit() user details
//
}
