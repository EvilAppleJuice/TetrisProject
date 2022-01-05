/*
Part of the Tetris Project. Will hold the game display.
Logan Fontenot & Logan Mancilla
11/10/21
 */

import java.awt.event.*;
import javax.swing.*;

public class TetrisWindow extends JFrame{
    private TetrisGame game;
    private TetrisDisplay display;
    
    private int rows = 20;
    private int cols = 10;
    private int win_width = 400;
    private int win_height = 400;
    
    public TetrisWindow()
    {
        this.setTitle("My Tetris Game: Part 3        Logans");
        this.setSize(win_width,win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game = new TetrisGame(rows, cols);
        display = new TetrisDisplay(game);
        this.add(display);
        
        initMenus();
        
        this.setVisible(true);
    }
    
    private void initMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu leaderMenu = new JMenu("Leaderboard");
        JMenu optionMenu = new JMenu("Game Options");
        menuBar.add(leaderMenu);
        menuBar.add(optionMenu);
        
        JMenuItem leaderboard = new JMenuItem("Leaderboard Scores");
        JMenuItem clearHistory = new JMenuItem("Clear Leaderboard");
        leaderMenu.add(leaderboard);
        leaderMenu.add(clearHistory);
        
        JMenuItem newGame = new JMenuItem("Start New Game");
        JMenuItem saveGame = new JMenuItem("Save Current Game");
        JMenuItem loadGame = new JMenuItem("Load Saved Game");
        optionMenu.add(newGame);
        optionMenu.addSeparator();
        optionMenu.add(saveGame);
        optionMenu.add(loadGame);
        
        leaderboard.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ac)
            {
                game.viewLeaderboard();
            }
        });
            
        clearHistory.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ac)
            {
                game.clearLeaderBoard();
            }
        });
        
        newGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ac)
            {
                game.newGame();
                display.animationBump();
            }
        });
        
        saveGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ac)
            {
                game.saveToFile("tetrisSave.txt");
            }
        });
            
        loadGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ac)
            {
                game.initFromFile("tetrisSave.txt");
                game.resetState();
                display.animationBump();                
            }
        });
        
        this.setJMenuBar(menuBar);
    }    
    
    public static void main(String[] args) 
    {
        new TetrisWindow();
    }
}
