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
    Car[] cars = board.getCars();
    Thread car1, car2, car3, car4, car5, car6;

    car1 = new Thread(cars[0]);
    car2 = new Thread(cars[1]);
    car3 = new Thread(cars[2]);
    car4 = new Thread(cars[3]);
    car5 = new Thread(cars[4]);
    car6 = new Thread(cars[5]);
    
    car1.start();
    car2.start();
    car3.start();
    car4.start();
    car5.start();
    car6.start();

    Thread player1Thread, player2Thread;
    player1Thread = new Thread(player1);
    player2Thread = new Thread(player2);
    player2Thread.start();
    player1Thread.start();

    game.setVisible(true);

  }
}
