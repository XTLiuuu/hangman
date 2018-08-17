
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  When run as a program, this class opens a window on the screen that
 *  shows a large number of colored disks.  The positions of the disks
 *  are selected at random, and the color is randomly selected from
 *  red, green, or blue.  A black outline is drawn around each disk.
 *  The picture changes every three seconds.
 */
public class MouseDrawDemo extends JPanel {

  int left;

  public MouseDrawDemo(int left){
    this.left = left;
  }

  public void drawFrame(Graphics g, int width, int height) {
    g.drawLine(70,40,width-350,40);
    g.drawLine(70,200,width-70,200);
    g.drawLine(100,40,100,200);
    if(this.left==6){
      g.drawLine(300,40,300,70);
    }
    else if(this.left==5){
      g.drawLine(300,40,300,70);
      g.drawOval(285,70,30,30);
    }
    else if(this.left==4){
      g.drawLine(300,40,300,70);
      g.drawOval(285,70,30,30);
      g.drawLine(300,100,300,130);
    }
    else if(this.left==3){
      g.drawLine(300,40,300,70);
      g.drawOval(285,70,30,30);
      g.drawLine(300,100,300,130);
      g.drawLine(288,110,268,128);
    }
    else if(this.left==2){
      g.drawLine(300,40,300,70);
      g.drawOval(285,70,30,30);
      g.drawLine(300,100,300,130);
      g.drawLine(288,110,268,128);
      g.drawLine(312,110,332,128);
    }
    else if(this.left==1){
      g.drawLine(300,40,300,70);
      g.drawOval(285,70,30,30);
      g.drawLine(300,100,300,130);
      g.drawLine(288,110,268,128);
      g.drawLine(312,110,332,128);
      g.drawLine(298,135,285,160);
    }
    else if(this.left==0){
      g.drawLine(300,40,300,70);
      g.drawOval(285,70,30,30);
      g.drawLine(300,100,300,130);
      g.drawLine(288,110,268,128);
      g.drawLine(312,110,332,128);
      g.drawLine(298,135,285,160);
      g.drawLine(302,135,315,160);
    }
  }

  public static void main(String[] args) {

      JFrame window = new JFrame("Random Disks");
      MouseDrawDemo drawingArea = new MouseDrawDemo(6);

      drawingArea.setBackground(Color.WHITE);
      window.setContentPane(drawingArea);
      drawingArea.setPreferredSize(new Dimension(500,500));
      window.pack();
      window.setLocation(100,50);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setResizable(true);
      window.setVisible(true);
  } // end main

  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      drawFrame(g, getWidth(), getHeight());
  }

}
