package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.Observer;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    if (stage.getScene() == null) {
      stage.setScene(new Scene(render()));
    } else {
      stage.getScene().setRoot(render());
    }
    if (!stage.isFullScreen()) {
      stage.setFullScreen(true);
    }
  }
}
