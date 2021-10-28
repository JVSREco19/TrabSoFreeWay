package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class FreeWay extends JFrame{
  public FreeWay(){
    initUI();
  }

  private void initUI(){
    add(new Board());
    setTitle("FreeWay - Trabalho de SO");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Utils.WIDTH, Utils.HEIGHT);
  
    setResizable(false);
    setVisible(true);
  }

  public static void main (String[] args) {
    EventQueue.invokeLater(new Runnable() {

      @Override
      public void run() {
        FreeWay game = new FreeWay();
        game.setVisible(true);
        
      }
      
    });
  }
}
