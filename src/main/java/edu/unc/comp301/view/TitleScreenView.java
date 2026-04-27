package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Difficulty;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

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
    title.getStyleClass().add("title");

    StackPane layoutHighScore = new StackPane();
    Label highScoreLabel = new Label("High Score: " + model.getHighScore());
    highScoreLabel.getStyleClass().add("score");
    layoutHighScore.getChildren().add(highScoreLabel);

    StackPane layoutLastScore = new StackPane();
    Label lastScoreLabel = new Label("Last Score: " + model.getCurScore());
    lastScoreLabel.getStyleClass().add("score-small");
    layoutLastScore.getChildren().add(lastScoreLabel);

    StackPane layoutButton = new StackPane();
    Button buttonStartGame = new Button("Start Game");
    buttonStartGame.setOnAction(e -> controller.startGame());
    buttonStartGame.getStyleClass().add("start-button");
    layoutButton.getChildren().add(buttonStartGame);

    StackPane layoutCredits = new StackPane();
    Label creditLabel = new Label("By Taras Brytskyy");
    creditLabel.getStyleClass().add("credit");
    layoutCredits.getChildren().add(creditLabel);

    layout
        .getChildren()
        .addAll(title, layoutHighScore, layoutLastScore, layoutButton, layoutCredits);
    layout.setPadding(new Insets(40));
    layout.setSpacing(15);
    layout.setAlignment(javafx.geometry.Pos.CENTER);

    // ------------------------------------//
    // Add two buttons to the right
    VBox rightLayout = new VBox();
    rightLayout.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

    Button btn1 = new Button("Change Themes");
    Button btn2 = new Button("Toggle gamemode");
    StackPane gamemodeBox = new StackPane();
    Label gamemode =
        new Label(((ModelImpl) model).getDifficulty() == Difficulty.HARD ? "HARD" : "EASY");

    btn1.getStyleClass().add("toggle-view-button");
    btn2.getStyleClass().add("game-mode-button");

    if (((ModelImpl) model).getDifficulty() == Difficulty.EASY) {
      gamemode.setStyle("-fx-text-fill: #ffcc00;");
    } else {
      gamemode.setStyle("-fx-text-fill: #ff0000;");
    }

    gamemode.getStyleClass().add("game-mode-label");
    rightLayout.setPadding(new Insets(50));
    rightLayout.setSpacing(15);

    gamemodeBox.getChildren().add(gamemode);
    rightLayout.getChildren().addAll(btn1, btn2, gamemodeBox);

    BorderPane rootLayout = new BorderPane();
    rootLayout.setCenter(layout);
    rootLayout.setRight(rightLayout);

    Region spacer = new Region();
    spacer.prefWidthProperty().bind(rightLayout.widthProperty());
    rootLayout.setLeft(spacer);

    rootLayout.getStyleClass().add("menu-root");
    rootLayout
        .getStylesheets()
        .add(getClass().getResource("/stylesheets/title_view1.css").toExternalForm());

    // Change the theme when the button is pressed
    btn1.setOnAction(
        e -> {
          ModelImpl m = (ModelImpl) model;

          if (m.getTheme() == 1) {
            m.setTheme(2);
            rootLayout.getStylesheets().clear();
            rootLayout
                .getStylesheets()
                .add(getClass().getResource("/stylesheets/title_view2.css").toExternalForm());
          } else {
            m.setTheme(1);
            rootLayout.getStylesheets().clear();
            rootLayout
                .getStylesheets()
                .add(getClass().getResource("/stylesheets/title_view1.css").toExternalForm());
          }
        });

    // Change the difficulty
    btn2.setOnAction(
        e -> {
          if (((ModelImpl) model).getDifficulty() == Difficulty.EASY) {
            gamemode.setText("HARD");
            ((ModelImpl) model).setDifficulty(Difficulty.HARD);
            gamemode.setStyle("-fx-text-fill: #ff0000;");
          } else {
            gamemode.setText("EASY");
            ((ModelImpl) model).setDifficulty(Difficulty.EASY);
            gamemode.setStyle("-fx-text-fill: #ffcc00;");
          }
        });

    return rootLayout;
  }
}
