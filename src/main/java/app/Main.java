package app;

import app.events.SetPrimaryStageEvent;
import app.injector.FXMLLoaderAndInjector;
import app.injector.InjectorConfiguration;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Antic on 25.07.2016.
 */
public class Main extends Application
{
  private Stage stage;
  private BorderPane borderPaneMenu;
  private AnchorPane anchorPanePersonOverview;

  private FXMLLoaderAndInjector injectorInstance;

  @Override
  public void start(Stage myStage) throws Exception {
    this.stage = myStage;

    initInjector();
    initMenu();
    initPersonOverview();
  }

  private void initInjector() {
    Injector injector = Guice.createInjector(new InjectorConfiguration());
    injector.getInstance(FXMLLoaderAndInjector.class);
  }

  private void initMenu() throws IOException {
    borderPaneMenu = FXMLLoaderAndInjector.loadNode(BorderPane.class, "/view/RootLayout.fxml");
    Scene scene = new Scene(borderPaneMenu);
    stage.setScene(scene);
    stage.show();
  }

  private void initPersonOverview() throws IOException {
    anchorPanePersonOverview = FXMLLoaderAndInjector.loadNodeRegisterAndInjectDependencies(AnchorPane.class, "/view/PersonOverview.fxml");
    FXMLLoaderAndInjector.permittStageToReceivers(new SetPrimaryStageEvent(stage));
    borderPaneMenu.setCenter(anchorPanePersonOverview);
  }

  public static void main(String[] args) {
    launch(args);
  }
}