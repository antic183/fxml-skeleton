package app.injector;

import app.Main;
import app.events.SetPrimaryStageEvent;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Antic on 27.07.2016.
 */
public class FXMLLoaderAndInjector<T extends Pane>
{
  private static EventBus eventBus;
  private static Injector injector;

  @Inject
  private void inject(Injector injector, EventBus eventBus) {
    this.eventBus = eventBus;
    this.injector = injector;
  }

  // load only. without passing the eventbus to the controller!
  public static <T> T loadNode(Class<T> type, String url) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(Main.class.getResource(url));
    return loader.load();
  }

  public static <T> T loadNodeRegisterAndInjectDependencies(Class<T> type, String url) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(Main.class.getResource(url));
    T node = loader.load();
    registerAndInjectDependencies(loader.getController());

    return node;
  }

  public static void permittStageToReceivers(SetPrimaryStageEvent eventObject) {
    eventBus.post(eventObject);
  }

  private static void registerAndInjectDependencies(Object controller) {
    eventBus.register(controller);
    try {
      injector.injectMembers(controller);
    } catch (Exception e) {
      System.err.println("undefined controller! A controller is required by injecting.");
    }

  }
}
