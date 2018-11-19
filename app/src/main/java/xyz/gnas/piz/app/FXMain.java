package xyz.gnas.piz.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.greenrobot.eventbus.EventBus;
import xyz.gnas.piz.app.common.ResourceManager;
import xyz.gnas.piz.app.common.Utility;
import xyz.gnas.piz.app.events.ExitEvent;

import static xyz.gnas.piz.app.common.Utility.writeErrorLog;

public class FXMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> writeErrorLog(getClass(), "Uncaught " +
                    "exception", e));

            stage.setOnCloseRequest((WindowEvent arg0) -> {
                // raise exit event
                EventBus.getDefault().post(new ExitEvent(arg0));
            });

            FXMLLoader loader = new FXMLLoader(ResourceManager.getAppFXML());
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().addAll(ResourceManager.getCSSList());
            stage.setScene(scene);
            stage.setTitle("Piz");
            stage.getIcons().add(ResourceManager.getAppIcon());
            stage.show();
        } catch (Exception e) {
            Utility.showError(getClass(), e, "Could not start the application", true);
        }
    }
}