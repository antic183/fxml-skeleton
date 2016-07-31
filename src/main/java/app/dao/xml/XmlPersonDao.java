package app.dao.xml;

import app.dao.InterPersonDao;
import app.model.Person;
import javafx.collections.ObservableList;

/**
 * Created by Antic on 01.08.2016.
 */
public class XmlPersonDao implements InterPersonDao
{
  @Override
  public ObservableList<Person> getAllPersons() {
    return null;
  }

  @Override
  public void addPerson(Person person) throws Exception {
// todo
  }

  @Override
  public void updatePerson(Person person) throws Exception {
// todo
  }

  @Override
  public void deletePerson(Person person) throws Exception {
// todo
  }

  @Override
  public void deleteAllPersons() {
// todo
  }

  @Override
  public void cleanAndClose() {
// todo
  }
}
