package edu.unc.comp301.view;

import edu.unc.comp301.model.Observer;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class View implements FXComponent, Observer {

  public View() {}

  public Parent render() {
    Pane s = new StackPane();
    s.getChildren().add(new Label("Hello, World"));
    return s;
  }

  @Override
  public void update() {}
}
