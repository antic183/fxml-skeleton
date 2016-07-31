package app.dao;

import app.model.Person;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Antic on 27.07.2016.
 */
public interface InterPersonDao
{
  public ObservableList<Person> getAllPersons();

  public void addPerson(Person person) throws Exception;

  public void updatePerson(Person person) throws Exception;

  public void deletePerson(Person person) throws Exception;

  public void deleteAllPersons();

  public void cleanAndClose();
}