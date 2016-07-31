package app.dao.json;

import app.dao.InterPersonDao;
import app.dao.util.IdGenerator;
import app.model.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.inject.Singleton;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Antic on 31.07.2016.
 */
@Singleton
public class JsonPersonDao implements InterPersonDao
{
  private ObservableList<Person> persons = FXCollections.observableArrayList();

  private final String DATA_FILE = "persons.json";

  private final GsonBuilder gsonBuilder;
  private final Gson gson;

  public JsonPersonDao() {
    gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
    gson = gsonBuilder.create();
    readPersonsFromFile();
  }

  @Override
  public ObservableList<Person> getAllPersons() {
    return persons;
  }

  @Override
  public void addPerson(Person person) throws Exception {
    person.setId(IdGenerator.getNext());
    persons.add(person);
    savePersonsIntoFile();
  }

  @Override
  public void updatePerson(Person person) throws Exception {
    if (persons.contains(person)) {
      int index = persons.indexOf(person);
      persons.set(index, person);
      savePersonsIntoFile();
    }
  }

  @Override
  public void deletePerson(Person person) throws Exception {
    if (persons.contains(person)) {
      persons.remove(person);
      savePersonsIntoFile();
    }
  }

  @Override
  public void deleteAllPersons() {
    persons.clear();
    try {
      savePersonsIntoFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void cleanAndClose() {

  }

  private void readPersonsFromFile() {
    File dataFile = new File(DATA_FILE);
    if (dataFile.exists()) {
      try {
        Type type = new TypeToken<ObservableList<Person>>() {}.getType();
        BufferedReader br = new BufferedReader(new FileReader(dataFile));
        ArrayList<Person> readedData = gson.fromJson(br, type);
        persons.clear();
        persons.addAll(readedData);
        br.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void savePersonsIntoFile() throws Exception {
    String json = gson.toJson(persons);
    Writer writer = null;
    try {
      writer = new FileWriter(DATA_FILE);
      writer.write(json);
      writer.close();
    } catch (IOException e) {
      throw new Exception("Error: doesn't saved data to json file!");
    }
  }

}
