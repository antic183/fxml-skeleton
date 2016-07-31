package app.events;

import javafx.stage.Stage;

/**
 * Created by Antic on 29.07.2016.
 */
public class SetPrimaryStageEvent
{
  private Stage rootStage;

  public SetPrimaryStageEvent(Stage stage) {
    rootStage = stage;
  }

  public Stage getStage() {
    return rootStage;
  }
}
