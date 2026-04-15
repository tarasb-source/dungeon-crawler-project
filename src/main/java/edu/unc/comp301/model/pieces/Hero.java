package edu.unc.comp301.model.pieces;

public class Hero extends APiece implements MovablePiece {

  public Hero() {
    super("Hero", "src/main/resources/Hero.css");
  }

  public Hero(String name, String resourcePath) {
    super(name, resourcePath);
  }

  @Override
  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else if (other instanceof Treasure) {
      return new CollisionResult(((Treasure) other).getValue(), CollisionResult.Result.CONTINUE);
    } else if (other instanceof Enemy) {
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    } else if (other instanceof Exit) {
      return new CollisionResult(0, CollisionResult.Result.NEXT_LEVEL);
    } else {
      throw new IllegalArgumentException();
    }
  }


}
