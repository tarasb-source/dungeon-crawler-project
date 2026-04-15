package edu.unc.comp301.model.pieces;

public class Wall extends APiece {

  public Wall() {
    super("Wall", "src/main/resources/Wall.css");
  }

  public Wall(String name, String resourcePath) {
    super(name, resourcePath);
  }
}
