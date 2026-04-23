package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class View implements FXComponent, Observer {
  private final Controller controller;
  private final Model model;
  private Stage stage;

  public View(Controller controller, Model model, Stage stage) {
    this.controller = controller;
    this.model = model;
    this.stage = stage;
  }

  public Parent render() {
    Parent view;
    if (model.getStatus() == Model.STATUS.IN_PROGRESS) {
      view = new GameView(controller, model).render();
    } else {
      view = new TitleScreenView(controller, model).render();
    }
    return view;
  }

  @Override
  public void update() {
    Scene newScene = new Scene(render());
    stage.setScene(newScene);
  }
}
