package app.events;

import app.model.Person;

/**
 * Created by Antic on 30.07.2016.
 */
public class SetPersonEditEvent
{
  private Person person;
  private boolean newEntry;

  public SetPersonEditEvent(Person person, boolean newEntry) {
    this.person = person;
    this.newEntry = newEntry;
  }

  public SetPersonEditEvent(boolean newEntry) {
    this.newEntry = newEntry;
  }

  public Person getPerson() {
    return person;
  }

  public boolean isNewEntry() {
    return newEntry;
  }
}
