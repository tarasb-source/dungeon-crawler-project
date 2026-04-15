package edu.unc.comp301.model.board;

import edu.unc.comp301.model.pieces.*;
import java.util.*;
import java.util.function.Supplier;

public class BoardImpl implements Board {
  Random random = new Random();
  private Piece[][] board;
  private Map<Posn, Piece> piecesPositions;
  private int width, height, numAvailableSpots;
  private Posn heroPosition;

  public BoardImpl(int width, int height) {
    this.board = new Piece[height][width];
    this.piecesPositions = new HashMap<>();
    this.heroPosition = null;
    this.width = width;
    this.height = height;
    this.numAvailableSpots = width * height;
    boardInit();
  }

  public BoardImpl(Piece[][] board) {
    this.board = board;
    this.piecesPositions = new HashMap<>();
    this.heroPosition = null;
    this.width = board[0].length;
    this.height = board.length;
    this.numAvailableSpots = width * height;
    scanBoard();
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
    piecesPositions.clear();
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
        p.setPosn(posn);
        board[randRow][randCol] = p;
        piecesPositions.put(posn, p);
        if (p instanceof Hero) {
          heroPosition = posn;
        }
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
    int newRow = heroPosition.getRow() + drow;
    int newCol = heroPosition.getCol() + dcol;
    Posn newPos = new Posn(newRow, newCol);

    // Check if the move is illegal
    if ((newRow < 0 || newRow >= height || newCol < 0 || newCol >= width)
        || (piecesPositions.get(newPos) instanceof Wall)) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }

    // Hero can move
    Hero hero = (Hero) piecesPositions.get(heroPosition);
    CollisionResult heroCollision = hero.collide(piecesPositions.get(newPos));
    if (heroCollision.getResults() == CollisionResult.Result.GAME_OVER) {
      return heroCollision;
    }
    // Remove old position
    piecesPositions.remove(heroPosition);
    board[heroPosition.getRow()][heroPosition.getCol()] = null;

    // Add hero to the new position
    piecesPositions.put(newPos, hero);
    board[newRow][newCol] = hero;
    heroPosition = newPos;

    if (heroCollision.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      return heroCollision;
    }

    // Now enemies move
    Iterator<Posn> it = new ArrayList<>(piecesPositions.keySet()).iterator();
    while (it.hasNext()) {
      Posn posn = it.next();
      Piece p = piecesPositions.get(posn);
      if (!(p instanceof Enemy)) {
        continue;
      }

      Enemy enemy = (Enemy) p;
      Directions dir = Directions.values()[random.nextInt(Directions.values().length)];
      int newEnemyRow = posn.getRow() + dir.getDRow();
      int newEnemyCol = posn.getCol() + dir.getDCol();

      if (newEnemyRow < 0 || newEnemyRow >= height || newEnemyCol < 0 || newEnemyCol >= width) {
        continue;
      }

      Posn randEnemyMove = new Posn(newEnemyRow, newEnemyCol);
      if (piecesPositions.get(randEnemyMove) instanceof Enemy) {
        continue;
      }
      CollisionResult enemyCollision = ((Enemy) p).collide(piecesPositions.get(randEnemyMove));
      if (enemyCollision.getResults() == CollisionResult.Result.CONTINUE) {
        piecesPositions.remove(posn);
        board[posn.getRow()][posn.getCol()] = null;
        piecesPositions.put(randEnemyMove, enemy);
        board[newEnemyRow][newEnemyCol] = enemy;
      } else {
        return enemyCollision;
      }
    }
    return new CollisionResult(heroCollision.getPoints(), CollisionResult.Result.CONTINUE);
  }

  private enum Directions {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int dCol;
    private final int dRow;

    private Directions(int dRow, int dCol) {
      this.dRow = dRow;
      this.dCol = dCol;
    }

    public int getDRow() {
      return dRow;
    }

    public int getDCol() {
      return dCol;
    }
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

  private void scanBoard() {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Piece p = board[row][col];
        if (p != null) {
          Posn posn = new Posn(row, col);
          p.setPosn(posn);
          piecesPositions.put(posn, p);
          if (p instanceof Hero) {
            heroPosition = posn;
          }
        }
      }
    }
  }
}
