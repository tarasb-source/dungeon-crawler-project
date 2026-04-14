package edu.unc.comp301.model.board;

import edu.unc.comp301.model.pieces.CollisionResult;
import edu.unc.comp301.model.pieces.Piece;

public class BoardImpl implements Board {
  @Override
  public void init(int enemies, int treasures, int walls) {}

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public Piece get(Posn posn) {
    return null;
  }

  @Override
  public void set(Piece p, Posn newPos) {}

  @Override
  public CollisionResult moveHero(int drow, int dcol) {
    return null;
  }
}
