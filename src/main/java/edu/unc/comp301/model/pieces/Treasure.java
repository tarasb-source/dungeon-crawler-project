package edu.unc.comp301.model.pieces;

public class Treasure extends APiece {
  private final int value;

  public Treasure(String name, String resourcePath, int value) {
    super(name, resourcePath);
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
