package app.controller;

import app.dao.InterPersonDao;
import app.events.PersonEditedEvent;
import app.events.SetDialogStageEvenet;
import app.events.SetPersonEditEvent;
import app.model.Person;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.inject.Singleton;

/**
 * Created by Antic on 27.07.2016.
 */
@Singleton
public class PersonEditController
{

  @Inject
  private EventBus eventBus;
  @Inject
  private InterPersonDao db;

  private Person selectedPerson;
  private Stage dialogStage;
  private boolean newEntry;

  @FXML
  private TextField txtFieldFirstName;
  @FXML
  private TextField txtFieldLastName;

  @FXML
  public void initialize() {
    System.out.println("initialize " + this.getClass().getSimpleName());
  }

  @FXML
  private void cancelClicked() {
    dialogStage.close();
  }

  @FXML
  private void saveClicked() {
    if (validateInput()) {
      if (newEntry) {
        Person newPerson = new Person(txtFieldFirstName.getText(), txtFieldLastName.getText());
        try {
          db.addPerson(newPerson);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
      } else {
        selectedPerson.setFirstName(txtFieldFirstName.getText());
        selectedPerson.setLastName(txtFieldLastName.getText());
        try {
          db.updatePerson(selectedPerson);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }
        eventBus.post(new PersonEditedEvent(true));
      }
      dialogStage.close();
    } else {
      showInvalidInputMsg();
    }
  }

  private boolean validateInput() {
    String firstName = txtFieldFirstName.getText();
    String lastName = txtFieldLastName.getText();
    boolean inputValid = true;

    if (firstName.trim().length() < 1 || lastName.trim().length() < 1) {
      inputValid = false;
    }

    return inputValid;
  }

  private void showInvalidInputMsg() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.initOwner(dialogStage);
    alert.setTitle("ERROR !!");
    alert.setHeaderText("Not valid input.");
    alert.setContentText("First Name and Last Name are required!");

    alert.showAndWait();
  }

  @Subscribe
  private void addSelectedPerson(SetPersonEditEvent eventObject) {
    newEntry = eventObject.isNewEntry();

    if (!newEntry) {
      selectedPerson = eventObject.getPerson();
      txtFieldFirstName.setText(eventObject.getPerson().getFirstName());
      txtFieldLastName.setText(eventObject.getPerson().getLastName());
    } else {
      selectedPerson = null;
      txtFieldFirstName.setText("");
      txtFieldLastName.setText("");
    }
  }

  @Subscribe
  private void addDialogStage(SetDialogStageEvenet eventObject) {
    dialogStage = eventObject.getDialogStage();
  }
}
