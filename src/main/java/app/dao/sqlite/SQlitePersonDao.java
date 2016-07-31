package app.dao.sqlite;

import app.dao.InterPersonDao;
import app.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.io.File;
import java.sql.*;

/**
 * Created by Antic on 31.07.2016.
 */
@Singleton
public class SQlitePersonDao implements InterPersonDao
{

  /*
  * TODO: create abstract SQLPersonDao -> extends SQlitePersonDao, MySqlPersonDao
  */

  private final String URL;
  private final String TABLE_NAME = "persons";
  Connection conn = null;

  private ObservableList<Person> persons = FXCollections.observableArrayList();

  public SQlitePersonDao() {
    this.URL = "jdbc:sqlite:test.db";
    connect();
  }

  private void connect() {
    try {
      conn = DriverManager.getConnection(URL);
      Statement stmt = conn.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "\n"
          + "( id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
          + "  firstname VARCHAR NOT NULL,\n"
          + "  lastname VARCHAR NOT NULL\n"
          + ");";
      stmt.execute(sql);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Override
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
      stmt.close();
      return persons;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void addPerson(Person person) throws Exception {
    try {
      String query = "INSERT INTO " + TABLE_NAME + "(firstname, lastname) values(?, ?)";
      PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, person.getFirstName());
      preparedStatement.setString(2, person.getLastName());
      preparedStatement.execute();
      ResultSet rs = preparedStatement.getGeneratedKeys();
      preparedStatement.close();
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

  @Override
  public void updatePerson(Person person) throws Exception {
    if (person.getId() != 0) {
      try {
        String query = "UPDATE " + TABLE_NAME + " SET firstname=?, lastname=? where id=(?)";
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

  @Override
  public void deletePerson(Person person) throws Exception {
    if (person.getId() != 0) {
      try {
        String query = "DELETE FROM " + TABLE_NAME + " where id=(?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, person.getId());
        preparedStatement.execute();
        persons.remove(person);
      } catch (SQLException e) {
        throw new Exception("SQL Delete Exception!");
      }
    } else {
      throw new Exception("Person haven't set property id. Person must have a valid id!");
    }
  }

  @Override
  public void deleteAllPersons() {
    // todo
  }

  @Override
  public void cleanAndClose() {
    try {
      conn.close();
      System.out.println("sql connection was closed!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
