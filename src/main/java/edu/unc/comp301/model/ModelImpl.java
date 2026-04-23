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
    curScore = 0;
    level = 1;
    try {
      board.init(level + 1, 2, 2);
    } catch (IllegalArgumentException e) {
      endGame();
      return;
    }
    notifyObservers();
  }

  @Override
  public void endGame() {
    if (curScore > highScore) {
      highScore = curScore;
    }
    status = STATUS.END_GAME;
    notifyObservers();
  }

  private void move(int drow, int dcol) {
    CollisionResult result = board.moveHero(drow, dcol);
    curScore += result.getPoints();
    if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      level++;
      try {
        board.init(level + 1, 2, 2);
      } catch (IllegalArgumentException e) {
        endGame();
        return;
      }
    } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
      if (curScore > highScore) {
        highScore = curScore;
      }
      endGame();
    }
    notifyObservers();
  }

  @Override
  public void moveUp() {
    move(-1, 0);
  }

  @Override
  public void moveDown() {
    move(1, 0);
  }

  @Override
  public void moveLeft() {
    move(0, -1);
  }

  @Override
  public void moveRight() {
    move(0, 1);
  }

  @Override
  public void addObserver(Observer o) {
    observers.add(o);
  }

  private void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}
