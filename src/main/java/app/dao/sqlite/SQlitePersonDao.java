package app.dao.sqlite;

import app.dao.InterPersonDao;
import app.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Antic on 31.07.2016.
 */
public class SQlitePersonDao implements InterPersonDao
{
  private ObservableList<Person> persons = FXCollections.observableArrayList();

  @Override
  public ObservableList<Person> getAllPersons() {
    return null;
  }

  @Override
  public void addPerson(Person person) throws Exception {

  }

  @Override
  public void updatePerson(Person person) throws Exception {

  }

  @Override
  public void deletePerson(Person person) throws Exception {

  }

  @Override
  public void deleteAllPersons() {

  }
}
