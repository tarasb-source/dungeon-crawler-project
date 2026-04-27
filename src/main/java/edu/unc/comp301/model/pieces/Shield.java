package edu.unc.comp301.model.pieces;

public class Shield extends APiece {

  public Shield() {
    super("Shield", "/images/shield.png");
  }

  public Shield(String name, String resourcePath, int value) {
    super(name, resourcePath);
  }
}
