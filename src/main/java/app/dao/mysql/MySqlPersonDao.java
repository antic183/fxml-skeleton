package app.dao.mysql;

import app.dao.InterPersonDao;
import app.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.sql.*;

/**
 * Created by Antic on 30.07.2016.
 */
@Singleton
public class MySqlPersonDao implements InterPersonDao
{
  private final String URL;
  private final String USER_NAME;
  private final String PASSWORD;
  private final String TABLE_NAME = "persons";
  private Connection conn;

  private ObservableList<Person> persons = FXCollections.observableArrayList();

  public MySqlPersonDao() {
    this.URL = "jdbc:mysql://localhost/test";
    this.USER_NAME = "javaUser";
    this.PASSWORD = "1234";
    connect();
  }

  private void connect() {
    try {
      conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    } catch (SQLException e) {
      try {
        conn.close();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      System.out.println(e.getMessage());
    }
  }

  public ObservableList<Person> getAllPersons() {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from " + TABLE_NAME);
      persons.clear();

      while (rs.next()) {
        long id = rs.getLong("id");
        String firstName = rs.getString("firstname");
        String lastName = rs.getString("lastname");
        persons.add(new Person(id, firstName, lastName));
      }
      rs.close();
      return persons;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void addPerson(Person person) throws Exception {
    try {
      String query = "INSERT INTO " + TABLE_NAME + "(firstname, lastname) values(?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, person.getFirstName());
      preparedStatement.setString(2, person.getLastName());
      preparedStatement.execute();
      ResultSet rs = preparedStatement.getGeneratedKeys();
      if (rs.next()) {
        int last_inserted_id = rs.getInt(1);
        // set the Person property id
        person.setId(last_inserted_id);
        // add inserted person to ObservableList
        persons.add(person);
      }
    } catch (SQLException e) {
      throw new Exception("SQL Insert Exception!");
    }
  }

  public void updatePerson(Person person) throws Exception {
    if (person.getId() != 0) {
      try {
        String query = "UPDATE " + TABLE_NAME + " SET firstname=?, lastname=? where id=(?) LIMIT 1";
        PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setLong(3, person.getId());
        preparedStatement.execute();
        preparedStatement.close();
      } catch (SQLException e) {
        throw new Exception("SQL Update Exception!");
      }
    } else {
      throw new Exception("Person haven't id. Person must have an id!");
    }
  }

  public void deletePerson(Person person) throws Exception {
    if (person.getId() != 0) {
      try {
        String query = "DELETE FROM " + TABLE_NAME + " where id=(?) LIMIT 1";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, person.getId());
        preparedStatement.execute();
        preparedStatement.close();
        persons.remove(person);
      } catch (SQLException e) {
        throw new Exception("SQL Delete Exception!");
      }
    } else {
      throw new Exception("Person haven't set property id. Person must have a valid id!");
    }
  }

  public void deleteAllPersons() {
    // todo
    System.out.println("delete all!");
  }

  @Override
  public void cleanAndClose() {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
