package edu.unc.comp301.controller;

import edu.unc.comp301.model.Model;

public class ControllerImpl implements Controller {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void moveUp() {
    model.moveUp();
  }

  @Override
  public void moveDown() {
    model.moveDown();
  }

  @Override
  public void moveLeft() {
    model.moveLeft();
  }

  @Override
  public void moveRight() {
    model.moveRight();
  }

  @Override
  public void startGame() {
    model.startGame();
  }

  public void endGame() {
    model.endGame();
  }

}
