package edu.unc.comp301.model.pieces;

public class Enemy extends APiece implements MovablePiece {

  public Enemy() {
    super("Enemy", "src/main/resources/Enemy.css");
  }

  public Enemy(String name, String resourcePath) {
    super(name, resourcePath);
  }

  @Override
  public CollisionResult collide(Piece other) {
    if (other == null || other instanceof Treasure || other instanceof Shield) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else if (other instanceof Hero) {
      if (((Hero) other).hasShield()) {
        return new CollisionResult(0, CollisionResult.Result.CONTINUE);
      }
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    } else {
      throw new IllegalArgumentException();
    }
  }
}
