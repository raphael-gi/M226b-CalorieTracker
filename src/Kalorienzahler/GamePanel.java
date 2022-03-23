package Kalorienzahler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    static final int screen_width = 600;
    static final int screen_height = 600;
    static final int unit_size = 25;
    static final int game_units = (screen_width*screen_height)/unit_size;
    static final int delay = 75;
    final int x[] = new int[game_units];
    final int y[] = new int[game_units];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    private JFrame frame;
    private Dimension size = new Dimension(1000,800);
    int enemyX;
    int enemyY;
    Random random;

    public GamePanel(){
        frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(size);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
    }
    public void startGame(){

    }
    public void paintComponent(Graphics g){

    }
    public void draw(Graphics g){
        for (int i = 0; i < screen_height/unit_size; i++){
            g.drawLine(i*unit_size, 0, i*unit_size, screen_height);
            g.drawLine(0, i*unit_size, screen_width, i*unit_size);
        }
    }
    public void move(){

    }
    public void checkHit(){

    }
    public void checkCollisions(){

    }
    public void gameOver(Graphics g){

    }
    public void newEnemy(){
        enemyX = random.nextInt(screen_width/unit_size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }
    }
}
