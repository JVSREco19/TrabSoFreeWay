package main;

import javax.swing.JFrame;

public class FreeWay extends JFrame{
  public FreeWay(){
    initUI();
  }

  private void initUI(){
    add(new Board());
    setTitle("FreeWay - Trabalho de SO");
    
  }
}
