package app.events;

import javafx.stage.Stage;

/**
 * Created by Antic on 30.07.2016.
 */
public class SetDialogStageEvenet
{
  private Stage dialogStage;

  public SetDialogStageEvenet(Stage stage) {
    dialogStage = stage;
  }

  public Stage getDialogStage() {
    return dialogStage;
  }
}
