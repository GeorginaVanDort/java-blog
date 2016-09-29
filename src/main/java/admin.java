import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public class Admin extends User {

  public Admin (String name, String email) {
    this.name = name;
    this.email = email;
    admin = true;
    }

  public List<User> returnAllUsers(){
    String sql = "select * from users;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(User.class);
    }
  }
//delete() users from DB
//return all() users
//ban() a user

}
