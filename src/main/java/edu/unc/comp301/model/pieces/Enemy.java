package edu.unc.comp301.model.pieces;

public class Enemy extends APiece implements MovablePiece {

  public Enemy() {}

  public Enemy(String name, String resourcePath) {
    super(name, resourcePath);
  }

  @Override
  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else if (other instanceof Treasure) {
      CollisionResult result = new CollisionResult(0, CollisionResult.Result.CONTINUE);
      other = null;
      return result;
    } else if (other instanceof Hero) {
      CollisionResult result = new CollisionResult(0, CollisionResult.Result.GAME_OVER);
      other = null;
      return result;
    } else {
      throw new IllegalArgumentException();
    }
  }
}
