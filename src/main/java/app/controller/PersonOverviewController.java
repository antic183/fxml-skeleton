package app.controller;

import app.dao.InterPersonDao;
import app.events.PersonEditedEvent;
import app.events.SetDialogStageEvenet;
import app.events.SetPersonEditEvent;
import app.events.SetPrimaryStageEvent;
import app.injector.FXMLLoaderAndInjector;
import app.model.Person;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Antic on 26.07.2016.
 */
public class PersonOverviewController
{
  @FXML
  private TableView<Person> personTable;
  @FXML
  private TableColumn<Person, String> firstNameColumn;
  @FXML
  private TableColumn<Person, String> lastNameColumn;
  @FXML
  private Label firstNameLabel;
  @FXML
  private Label lastNameLabel;


  @Inject
  private EventBus eventBus;
  @Inject
  private InterPersonDao db;
  @Inject
  private Stage rootStage;

  private Stage dialogStage;
  private Person selectedPerson;

  @FXML
  public void initialize() {
    System.out.println("initialize " + this.getClass().getSimpleName());
    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
  }

  @FXML
  private void newPerson() throws IOException {
    eventBus.post(new SetPersonEditEvent(true));
    showDialog("New Person");
  }

  @FXML
  private void editPerson() throws IOException {
    if (selectedPerson != null) {
      eventBus.post(new SetPersonEditEvent(selectedPerson, false));
      showDialog("Edit Person");
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.initOwner(rootStage);
      alert.setTitle("No Selection");
      alert.setHeaderText("No Person Selected");
      alert.setContentText("Please select a person in the table.");

      alert.showAndWait();
    }
  }

  @FXML
  private void deletePerson() {
    if (selectedPerson != null) {
      try {
        db.deletePerson(selectedPerson);
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.initOwner(rootStage);
      alert.setTitle("No Selection");
      alert.setHeaderText("No Person Selected");
      alert.setContentText("Please select a person in the table.");

      alert.showAndWait();
    }
  }

  private void showDialog(String title) throws IOException {
    dialogStage.setTitle(title);
    dialogStage.showAndWait();
  }

  private void initDialog() throws IOException {
    dialogStage = new Stage();
    dialogStage.initModality(Modality.WINDOW_MODAL);
    dialogStage.initOwner(rootStage);
    Scene scene = new Scene(FXMLLoaderAndInjector.loadNodeRegisterAndInjectDependencies(AnchorPane.class, "/view/PersonEdit.fxml"));
    dialogStage.setResizable(false);
    dialogStage.setScene(scene);

    eventBus.post(new SetDialogStageEvenet(dialogStage));
  }

  @Subscribe
  private void addStage(SetPrimaryStageEvent eventObject) throws IOException {
    rootStage = eventObject.getStage();
    showPersonDetails();
    addCellSelectionListener();
    initDialog();
  }

  @Subscribe
  private void personEditted(PersonEditedEvent eventObject) {
    if (eventObject.isPersonEditted()) {
      // refresh person details
      showPersonDetails();
    }
  }

  private void addCellSelectionListener() {
    personTable.setItems(db.getAllPersons());
    personTable.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
          selectedPerson = newValue;
          showPersonDetails();
        }
    );
  }

  private void showPersonDetails() {
    if (selectedPerson != null) {
      firstNameLabel.setText(selectedPerson.getFirstName());
      lastNameLabel.setText(selectedPerson.getLastName());
    } else {
      firstNameLabel.setText("");
      lastNameLabel.setText("");
    }
  }
}
