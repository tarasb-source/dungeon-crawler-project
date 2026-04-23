package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TitleScreenView implements FXComponent {
  private final Controller controller;
  private final Model model;

  public TitleScreenView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();

    Label title = new Label("Dungeon Crawler");
    title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

    StackPane layoutHighScore = new StackPane();
    Label highScoreLabel = new Label("High Score: " + model.getHighScore());
    highScoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    layoutHighScore.getChildren().add(highScoreLabel);

    StackPane layoutLastScore = new StackPane();
    Label lastScoreLabel = new Label("Last Score: " + model.getCurScore());
    lastScoreLabel.setStyle("-fx-font-size: 14px;");
    layoutLastScore.getChildren().add(lastScoreLabel);

    StackPane layoutButton = new StackPane();
    Button buttonStartGame = new Button("Start Game");
    buttonStartGame.setOnAction(e -> controller.startGame());
    layoutButton.getChildren().add(buttonStartGame);

    StackPane layoutCredits = new StackPane();
    Label creditLabel = new Label("By Taras Brytskyy");
    layoutCredits.getChildren().add(creditLabel);

    layout
        .getChildren()
        .addAll(title, layoutHighScore, layoutLastScore, layoutButton, layoutCredits);
    layout.setPadding(new Insets(40));
    layout.setSpacing(15);
    layout.setAlignment(javafx.geometry.Pos.CENTER);

    return layout;
  }
}
