/*
Part of the Tetris Project. Will create the display that will show the status of the game
Logan Fontenot & Logan Mancilla
11/10/21
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel{
    private TetrisGame game;
    private Timer timer;
    private Color[] colors = {Color.WHITE, Color.ORANGE, Color.BLUE, Color.GREEN,
                              Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN};   
        
    private int speed = 250;
    private int start_x = 140;
    private int start_y = 60;
    private int cell_size = 10;
    private int pauseCount = 1;
    
    public TetrisDisplay(TetrisGame gam)
    {
        game = gam;
        
        timer = new Timer(speed, new ActionListener(){
        public void actionPerformed(ActionEvent ae)
            {
                processMove();
            }
        });
                
        timer.start();
        this.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent ke)
            {                
                translateKey(ke);
            }
        });
        
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }
    
    public void translateKey(KeyEvent ke)
    {                
        int code = ke.getKeyCode();
        switch(code)
        {
            case KeyEvent.VK_SPACE:
                if (pauseCount%2 == 1)
                timer.stop();           
                else
                    timer.start();
                pauseCount++;
                break;
            case KeyEvent.VK_LEFT:   
                game.makeMove("left");
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove("right");
                break;
            case KeyEvent.VK_UP:
                game.rotateAndCheck();
                break;
            case KeyEvent.VK_N:
                game.newGame();
                animationBump();
                break;
        }
    }
    
    public void paintComponent(Graphics g)
    {        
        super.paintComponent(g);
        drawWell(g);        
        drawBackground(g);
        drawBrick(g);
        drawEndGame(g);
        drawScoreCounter(g);
    }

    public void drawBrick(Graphics g)
    {
        if(game.getState() == 1)
        {
            for(int segment=0; segment < game.getSegments() ; segment++)
            {
               int col = game.getSegmentCol(segment);
               int row = game.getSegmentRow(segment);

               g.setColor(colors[game.getColorIndex()]);
               g.fillRect(start_x+(col*cell_size),
               start_y+(row*cell_size), cell_size, cell_size);
               g.setColor(Color.BLACK);
               g.drawRect(start_x+(col*cell_size),
               start_y+(row*cell_size), cell_size, cell_size);
            }
        }
    }
    
    public void drawBackground(Graphics g)
    {
        g.setColor(colors[0]);
        g.fillRect(start_x, start_y, game.getCols()*cell_size, 
                   (game.getRows())*cell_size);
        for(int rows = 0; rows < game.getRows(); rows++)
        {
            for(int cols = 0; cols < game.getCols(); cols++)
            {
                if(game.getBackgroundPosition(rows, cols) != 0)
                {
                    g.setColor(colors[game.getBackgroundPosition(rows, cols)]);
                    g.fillRect(start_x+(cols*cell_size),
                    start_y+(rows*cell_size), cell_size, cell_size);
                    g.setColor(Color.BLACK);
                    g.drawRect(start_x+(cols*cell_size),
                    start_y+(rows*cell_size), cell_size, cell_size);
                }
            }
        }        
    }
    
    public void drawWell(Graphics g)
    {
        g.setColor(Color.BLACK);
        // Left wall
        g.fillRect(start_x-cell_size, start_y,
        cell_size, cell_size*game.getRows()+cell_size);
        // Right Wall
        g.fillRect(start_x+cell_size*game.getCols(), start_y,
        cell_size, cell_size*game.getRows()+cell_size);
        // Bottom Wall
        g.fillRect(start_x-cell_size, start_y+cell_size*game.getRows(),
        cell_size*game.getCols()+(2*cell_size), cell_size);        
    }
    
    public void drawEndGame(Graphics g)
    {
        if(game.getState() == 0)
        {    
            timer.stop();
            pauseCount++;
            
            int endGameWidth = 200;
            int endGameLength = 60;
            int endGameX = start_x + (cell_size*game.getCols())/2 - endGameWidth/2;
            int endGameY = start_y +(cell_size*game.getRows())/2 - endGameLength/2;
            
            g.setColor(colors[0]);
            g.fillRect(endGameX,endGameY, endGameWidth, endGameLength);
            g.setColor(Color.BLACK);
            g.drawRect(endGameX,endGameY, endGameWidth, endGameLength);
            
            int textCenterX = 5;
            int textCenterY = 40;
            int fontSize = 35;
            
            g.setFont(new Font("Ariel", Font.BOLD, fontSize));
            g.drawString("Game Over!", endGameX + textCenterX, endGameY + textCenterY);
        }    
    }
    
    public void drawScoreCounter(Graphics g)
    {
        int scoreWidth = 100;
        int scoreLength = 30;        
        
        g.setColor(colors[0]);
        g.fillRect(0, 0, scoreWidth, scoreLength);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, scoreWidth, scoreLength);
        
        int fontSize = 18;
        int textCenterX = 2;
        int textCenterY = 20;
        
        g.setFont(new Font("Ariel", Font.PLAIN, fontSize));
        g.drawString("Score: "+game.getScore(), textCenterX, textCenterY);
    }
    
    public void processMove()
    {
        game.makeMove("down");
        repaint();
    }
    
    public void animationBump()
    {        
        timer.start();
        if (pauseCount%2 == 0)
            pauseCount++;
    }
}
