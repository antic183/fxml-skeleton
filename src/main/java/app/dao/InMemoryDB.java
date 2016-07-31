package app.dao;

import app.model.Person;
import app.dao.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



import javax.inject.Singleton;

/**
 * Created by Antic on 27.07.2016.
 */
@Singleton
public class InMemoryDB implements InterPersonDao
{
  private ObservableList<Person> persons = FXCollections.observableArrayList();

  public InMemoryDB() {
    persons.add(new Person(IdGenerator.getNext(), "firstName 1", "lastName 1"));
    persons.add(new Person(IdGenerator.getNext(), "firstName 2", "lastName 2"));
  }

  public ObservableList<Person> getAllPersons() {
    return persons;
  }

  public void addPerson(Person person) {
    person.setId(IdGenerator.getNext());
    persons.add(person);
  }

  public void updatePerson(Person person) {
    if (persons.contains(person)) {
      // do nothing!
      System.out.println("update!");
    }
  }

  public void deletePerson(Person person) {
    System.out.println("delete one!");
  }

  public void deleteAllPersons() {
    persons.clear();
  }
}
