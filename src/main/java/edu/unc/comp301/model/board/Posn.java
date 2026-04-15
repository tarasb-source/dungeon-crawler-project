package edu.unc.comp301.model.board;

import java.util.Objects;

public class Posn {
  private final int row;
  private final int col;

  public Posn(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public String toString() {
    return row + "," + col;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Posn posn)) return false;
    return row == posn.row && col == posn.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
