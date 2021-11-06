package main;

import javax.swing.JFrame;

public class FreeWay extends JFrame {
  private Board board;

  public FreeWay() {
    initUI();
  }

  public Board getBoard() {
    return this.board;
  }

  private void initUI() {
    board = new Board();
    add(board);
    setTitle("FreeWay - Trabalho de SO");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Utils.WIDTH, Utils.HEIGHT);

    setResizable(false);
    setVisible(true);
  }

  public static void main(String[] args) {

    FreeWay game = new FreeWay();

    Board board = game.getBoard();
    Thread gameBoard = new Thread(board);
    gameBoard.start();

    Player player1 = board.getPlayer1();
    Player player2 = board.getPlayer2();

    player1.setSemaphores(board.getSemaphores());
    player2.setSemaphores(board.getSemaphores());

    Car[] cars = board.getCars();
    Thread threadCars[] = new Thread[Utils.NUMBER_OF_CARS];

    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
      threadCars[i] = new Thread(cars[i]);
      threadCars[i].start();
    }

    Thread player1Thread, player2Thread;

    player1Thread = new Thread(player1);
    player2Thread = new Thread(player2);

    player2Thread.start();
    player1Thread.start();

    game.setVisible(true);

  }
}
