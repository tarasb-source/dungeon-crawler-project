package edu.unc.comp301.view;

import edu.unc.comp301.controller.Controller;
import edu.unc.comp301.model.Difficulty;
import edu.unc.comp301.model.Model;
import edu.unc.comp301.model.ModelImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

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
    HBox.setHgrow(layout, Priority.ALWAYS);
    HBox.setMargin(layout, new Insets(0));
    layout.setAlignment(Pos.CENTER_LEFT);
    layout.setFillHeight(true);
    layout.setBackground(Background.fill(Paint.valueOf("grey")));

    GridPane board = new GridPane();
    GridPane.setMargin(board, new Insets(0));
    VBox.setVgrow(board, Priority.ALWAYS);
    HBox.setHgrow(board, Priority.ALWAYS);
    board.setAlignment(Pos.TOP_LEFT);
    board.setMaxHeight(Double.MAX_VALUE);
    board.setMaxWidth(Double.MAX_VALUE);
    board.setHgap(0);
    board.setVgap(0);

    StackPane currScorePane = new StackPane();
    Label currentScoreLabel = new Label("Current score: " + model.getCurScore());
    currentScoreLabel.setStyle(
        "-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #ffcc00;");
    currScorePane.getChildren().add(currentScoreLabel);
    Image shieldIcon = new Image("/images/shield.png");
    Label shieldLabel = new Label("Shield: Blocks one enemy attack");
    shieldLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
    shieldLabel.setWrapText(true);
    ImageView shieldView = new ImageView(shieldIcon);
    shieldView.setFitWidth(30);
    shieldView.setFitHeight(30);
    shieldView.setPreserveRatio(true);
    HBox shieldInfo = new HBox(shieldView, shieldLabel);
    shieldInfo.setSpacing(10);
    shieldInfo.setPadding(new Insets(5, 0, 5, 0));
    shieldInfo.setAlignment(Pos.CENTER_LEFT);

    Hero hero = ((ModelImpl) model).getHero();
    Label hasShield = new Label(hero.hasShield() ? "Shield: ACTIVE" : "Shield: NONE");
    hasShield.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {

        Piece piece = model.get(new Posn(row, col));

        StackPane cell = new StackPane();
        cell.prefWidthProperty().bind(board.widthProperty().divide(model.getWidth()));
        cell.prefHeightProperty().bind(layout.heightProperty().divide(model.getHeight()));

        String cellStyle = getCellStyle();
        cell.setStyle(cellStyle);

        if (piece != null) {
          ImageView imageView = getImageView(piece);
          imageView.fitWidthProperty().bind(cell.prefWidthProperty().multiply(0.8));
          imageView.fitHeightProperty().bind(cell.prefHeightProperty().multiply(0.8));
          imageView.setPreserveRatio(true);
          cell.getChildren().add(imageView);
        }

        board.add(cell, col, row);
      }
    }

    VBox allControls = new VBox();

    allControls.setSpacing(5);

    Button upButton = new Button("↑");
    StackPane stackForUpButton = new StackPane(upButton);
    upButton.setOnAction(e -> controller.moveUp());
    Button leftButton = new Button("←");
    leftButton.setOnAction(e -> controller.moveLeft());
    Button downButton = new Button("↓");
    downButton.setOnAction(e -> controller.moveDown());
    Button rightButton = new Button("→");
    rightButton.setOnAction(e -> controller.moveRight());

    upButton.setPrefSize(60, 60);
    leftButton.setPrefSize(60, 60);
    downButton.setPrefSize(60, 60);
    rightButton.setPrefSize(60, 60);

    upButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    downButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    leftButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    rightButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

    layout.setOnKeyPressed(
        e -> {
          switch (e.getCode()) {
            case W, UP -> controller.moveUp();
            case A, LEFT -> controller.moveLeft();
            case S, DOWN -> controller.moveDown();
            case D, RIGHT -> controller.moveRight();
          }
        });

    HBox otherControls = new HBox(leftButton, downButton, rightButton);
    otherControls.setSpacing(5);
    otherControls.setAlignment(Pos.CENTER);
    StackPane stackForOtherButtons = new StackPane(otherControls);
    allControls.getChildren().addAll(stackForUpButton, stackForOtherButtons);
    otherControls.setMaxWidth(Double.MAX_VALUE);

    VBox controlLayout = new VBox();
    controlLayout.setPadding(new Insets(20));
    controlLayout.setSpacing(30);
    controlLayout.setAlignment(Pos.CENTER);
    controlLayout.setMinWidth(300);
    controlLayout.setMaxWidth(300);
    controlLayout.getChildren().addAll(currScorePane, shieldInfo, hasShield, allControls);
    controlLayout.getStyleClass().add("controls-layout");

    layout.getChildren().addAll(board, controlLayout);

    // Add functionality to change the view based on what theme is it
    String css =
        ((ModelImpl) model).getTheme() == 1
            ? "/stylesheets/game_view1.css"
            : "/stylesheets/game_view2.css";
    layout.getStylesheets().add(getClass().getResource(css).toExternalForm());

    return layout;
  }

  private String getCellStyle() {
    String cellStyle;
    if (((ModelImpl) model).getTheme() == 1) {
      cellStyle =
          "-fx-border-color: #1a1a1a;"
              + "-fx-background-color: #2b2b2b;"
              + "-fx-border-color: #141414;"
              + "-fx-background-image: url('/images/stone_tile.png');"
              + "-fx-background-size: cover;";
    } else {
      cellStyle =
          "-fx-border-color: #2f5d2f;"
              + "-fx-background-color: #3fa34d;"
              + "-fx-border-color: #264f12;"
              + "-fx-background-image: url('/images/grass_tile2.png');"
              + "-fx-background-size: cover;";
    }
    return cellStyle;
  }

  private ImageView getImageView(Piece piece) {
    Image icon = null;
    if (piece instanceof Hero) {
      icon = new Image("/images/hero.png");
    } else if (piece instanceof Enemy) {
      if (((ModelImpl) model).getDifficulty() == Difficulty.EASY) {
        icon = new Image("/images/enemy_pink.png");
      } else {
        icon = new Image("/images/enemy_yellow.png");
      }
    } else if (piece instanceof Wall) {
      icon = new Image("/images/wall.png");
    } else if (piece instanceof Exit) {
      icon = new Image("/images/exit.png");
    } else if (piece instanceof Treasure) {
      icon = new Image("/images/treasure.png");
    } else if (piece instanceof Shield) {
      icon = new Image("/images/shield.png");
    }

    ImageView imageView = new ImageView(icon);
    imageView.setFitWidth(25);
    imageView.setFitHeight(25);
    return imageView;
  }
}
