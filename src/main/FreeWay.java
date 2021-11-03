package main;

import javax.swing.JFrame;

public class FreeWay extends JFrame{
  private Board board;
  public FreeWay(){
    initUI();
  }

  public Board getBoard(){
    return this.board;
  }

  private void initUI(){
    board = new Board();
    add(board);
    setTitle("FreeWay - Trabalho de SO");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Utils.WIDTH, Utils.HEIGHT);
  
    setResizable(false);
    setVisible(true);
  }

  public static void main (String[] args) {
    
        FreeWay game = new FreeWay();
        Board board = game.getBoard();
        Thread t1 = new Thread(board);
        t1.start();
        /*Player player = board.getPlayer();
        Car[] cars = board.getCars();
        Thread[] carsThread;
        Thread playerThread;*/

        game.setVisible(true);
  
  }
}
