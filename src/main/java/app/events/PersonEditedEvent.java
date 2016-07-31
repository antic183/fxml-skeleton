package app.events;

/**
 * Created by Antic on 30.07.2016.
 */
public class PersonEditedEvent
{
  private boolean personEdited;

  public PersonEditedEvent(boolean edited) {
    personEdited = edited;
  }

  public boolean isPersonEditted() {
    return personEdited;
  }
}
