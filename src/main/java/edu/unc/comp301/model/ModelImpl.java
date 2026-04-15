package edu.unc.comp301.model;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.BoardImpl;
import edu.unc.comp301.model.board.Posn;
import edu.unc.comp301.model.pieces.CollisionResult;
import edu.unc.comp301.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final Board board;
  private int curScore;
  private int highScore;
  private STATUS status;
  private int level;
  private final List<Observer> observers;

  public ModelImpl(int width, int height) {
    this.board = new BoardImpl(width, height);
    this.curScore = 0;
    this.highScore = 0;
    this.status = STATUS.END_GAME;
    this.level = 0;
    this.observers = new ArrayList<>();
  }

  public ModelImpl(Board board) {
    this.board = board;
    this.curScore = 0;
    this.highScore = 0;
    this.status = STATUS.END_GAME;
    this.level = 0;
    this.observers = new ArrayList<>();
  }

  @Override
  public int getWidth() {
    return board.getWidth();
  }

  @Override
  public int getHeight() {
    return board.getHeight();
  }

  @Override
  public Piece get(Posn p) {
    return board.get(p);
  }

  @Override
  public int getCurScore() {
    return curScore;
  }

  @Override
  public int getHighScore() {
    return highScore;
  }

  @Override
  public int getLevel() {
    return level;
  }

  @Override
  public STATUS getStatus() {
    return status;
  }

  @Override
  public void startGame() {
    status = STATUS.IN_PROGRESS;
    notifyObservers();
    curScore = 0;
    level = 1;
    int numOfEnemies = level + 1;
    int numOfTreasures = 2;
    int numOfWalls = 2;
  }

  @Override
  public void endGame() {
    status = STATUS.END_GAME;
    notifyObservers();
  }

  private void move(BoardImpl.Directions direction) {
    CollisionResult result =
        board.moveHero(direction.getDRow(), direction.getDCol());
    if (result.getPoints() > 0) {
      curScore++;
    }
    if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      level++;
      board.init(level, 2, 2);
    } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
      if (curScore > highScore) {
        highScore = curScore;
      }
      endGame();
    }
  }

  @Override
  public void moveUp() {
    move(BoardImpl.Directions.UP);
  }

  @Override
  public void moveDown() {
    move(BoardImpl.Directions.DOWN);
  }

  @Override
  public void moveLeft() {
    move(BoardImpl.Directions.LEFT);
  }

  @Override
  public void moveRight() {
    move(BoardImpl.Directions.RIGHT);
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  private void notifyObservers() {
    for (Observer o : observers) {
      o.notify();
    }
  }
}
