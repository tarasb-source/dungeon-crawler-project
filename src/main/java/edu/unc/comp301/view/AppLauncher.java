package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.controller.ControllerImpl;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    int width = 500;
    int height = 400;
    stage.setTitle("Taras's Dungeon Crawler");
    Model model = new ModelImpl(15, 10);
    Controller controller = new ControllerImpl(model);
    View view = new View(controller, model, stage);
    model.addObserver(view);

    Scene scene = new Scene(view.render(), width, height);
    scene.getStylesheets().add("stylesheets/dungeon.css");
    scene.addEventFilter(
        KeyEvent.KEY_PRESSED,
        event -> {
          if (event.getCode() == KeyCode.ESCAPE) {
            stage.close();
          }
        });

    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.setFullScreenExitHint("");
    stage.show();
  }
}
