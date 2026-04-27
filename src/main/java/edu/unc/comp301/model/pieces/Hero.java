package edu.unc.comp301.model.pieces;

public class Hero extends APiece implements MovablePiece {
  private boolean hasShield = false;

  public Hero() {
    super("Hero", "src/main/resources/Hero.css");
    hasShield = false;
  }

  public Hero(String name, String resourcePath) {
    super(name, resourcePath);
    hasShield = false;
  }

  @Override
  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else if (other instanceof Treasure) {
      return new CollisionResult(((Treasure) other).getValue(), CollisionResult.Result.CONTINUE);
    } else if (other instanceof Enemy) {
      if (hasShield) {
        useShield();
        return new CollisionResult(0, CollisionResult.Result.CONTINUE);
      }
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    } else if (other instanceof Exit) {
      return new CollisionResult(0, CollisionResult.Result.NEXT_LEVEL);
    } else if ((other instanceof Shield)) {
      giveShield();
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public boolean hasShield() {
    return hasShield;
  }

  public void giveShield() {
    hasShield = true;
  }

  public void useShield() {
    hasShield = false;
  }
}
