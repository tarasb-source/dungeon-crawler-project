package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameView implements FXComponent {
  private final Controller controller;
  private final Model model;

  public GameView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    GridPane board = new GridPane();

    StackPane currScorePane = new StackPane();
    Label currentScoreLabel = new Label("Current score: " + model.getCurScore());
    currentScoreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");
    currScorePane.getChildren().add(currentScoreLabel);

    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {

        Piece piece = model.get(new Posn(row, col));

        StackPane cell = new StackPane();
        cell.setPrefSize(30, 30);
        cell.setStyle("-fx-border-color: #444; -fx-background-color: #2c2c2c;");

        if (piece != null) {
          ImageView imageView = getImageView(piece);
          cell.getChildren().add(imageView);
        }

        board.add(cell, col, row);
      }
    }

    VBox allControls = new VBox();
    Button upButton = new Button("↑");
    StackPane stackForUpButton = new StackPane(upButton);
    upButton.setOnAction(e -> controller.moveUp());
    Button leftButton = new Button("←");
    leftButton.setOnAction(e -> controller.moveLeft());
    Button downButton = new Button("↓");
    downButton.setOnAction(e -> controller.moveDown());
    Button rightButton = new Button("→");
    rightButton.setOnAction(e -> controller.moveRight());

    HBox otherControls = new HBox(leftButton, downButton, rightButton);
    otherControls.setSpacing(5);
    otherControls.setAlignment(Pos.CENTER);
    StackPane stackForOtherButtons = new StackPane(otherControls);
    allControls.getChildren().addAll(stackForUpButton, stackForOtherButtons);
    allControls.setSpacing(5);

    VBox controlLayout = new VBox();
    controlLayout.setPadding(new javafx.geometry.Insets(20));
    controlLayout.setSpacing(30);
    controlLayout.setAlignment(Pos.TOP_CENTER);
    controlLayout.setMinWidth(150);
    controlLayout.getChildren().addAll(currScorePane, allControls);

    layout.getChildren().addAll(board, controlLayout);

    return layout;
  }

  private static ImageView getImageView(Piece piece) {
    Image icon = null;
    if (piece instanceof Hero) {
      icon = new Image("/images/hero.png");
    } else if (piece instanceof Enemy) {
      icon = new Image("/images/enemy_pink.png");
    } else if (piece instanceof Wall) {
      icon = new Image("/images/wall.png");
    } else if (piece instanceof Exit) {
      icon = new Image("/images/exit.png");
    } else if (piece instanceof Treasure) {
      icon = new Image("/images/treasure.png");
    }

    ImageView imageView = new ImageView(icon);
    imageView.setFitWidth(25);
    imageView.setFitHeight(25);
    return imageView;
  }
}
