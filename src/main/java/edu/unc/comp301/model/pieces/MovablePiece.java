package edu.unc.comp301.model.pieces;

public interface MovablePiece extends Piece {
  CollisionResult collide(Piece other);
}
