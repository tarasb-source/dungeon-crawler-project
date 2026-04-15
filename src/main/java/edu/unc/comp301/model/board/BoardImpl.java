package edu.unc.comp301.model.board;

import edu.unc.comp301.model.pieces.*;
import javafx.geometry.Pos;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class BoardImpl implements Board {
  Random random = new Random();
  private Piece[][] board;
  private Map<Posn, Piece> piecesPositions;
  private int width, height, numAvailableSpots;

  public BoardImpl(int width, int height) {
    this.board = new Piece[width][height];
    this.piecesPositions = new HashMap<>();
    this.width = width;
    this.height = height;
    this.numAvailableSpots = width * height;
    boardInit();
  }

  public BoardImpl(Piece[][] board) {
    this.board = board;
    this.piecesPositions = new HashMap<>();
    this.width = board[0].length;
    this.height = board.length;
    this.numAvailableSpots = width * height;
    boardInit();
  }

  private void boardInit() {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        board[row][col] = null;
      }
    }
  }

  @Override
  public void init(int enemies, int treasures, int walls) {
    boardInit();

    if ((enemies + treasures + walls + 1 + 1) > numAvailableSpots) {
      throw new IllegalArgumentException("Not enough space on a board");
    }
    initPieces(enemies, Enemy::new);
    initPieces(treasures, Treasure::new);
    initPieces(walls, Wall::new);
    initPieces(1, Hero::new);
    initPieces(1, Exit::new);
  }

  private void initPieces(int count, Supplier<Piece> supplier) {
    while (count > 0) {
      int randRow = random.nextInt(0, height);
      int randCol = random.nextInt(0, width);
      Posn posn = new Posn(randRow, randCol);
      if (!piecesPositions.containsKey(posn)) {
        Piece p = supplier.get();
        board[randRow][randCol] = p;
        piecesPositions.put(posn, p);
        count--;
        numAvailableSpots--;
      }
    }
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public Piece get(Posn posn) {
    return piecesPositions.get(posn);
  }

  @Override
  public void set(Piece p, Posn newPos) {
    board[newPos.getRow()][newPos.getCol()] = p;
    piecesPositions.put(newPos, p);
  }

  @Override
  public CollisionResult moveHero(int drow, int dcol) {
    return null;
  }

  @Override
  public String toString() {
    return "BoardImpl{"
        + "board="
        + Arrays.toString(board)
        + ", piecesPositions="
        + piecesPositions
        + ", width="
        + width
        + ", height="
        + height
        + ", numAvailableSpots="
        + numAvailableSpots
        + '}';
  }
}
