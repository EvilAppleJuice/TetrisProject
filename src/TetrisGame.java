/*
Part of the Tetris Project. Will dictate the games logic.
Logan Fontenot & Logan Mancilla
11/10/21
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class TetrisGame {
    private int[][] background;
    private int state = 1;
    private int rows;
    private int cols;
    private int score = 0;
    
    private Random randomGen;
    private TetrisBrick fallingBrick;
    
    public TetrisGame(int rs, int cs)
    {
        rows = rs;
        cols = cs;
        background = new int[rows][cols];
        randomGen = new Random();
        spawnBrick();
    }
    
    public void initBoard()
    {
        for(int row = 0; row < rows; row++)
        {
            for(int col = 0; col < cols; col++)
            {      
                background[row][col] = 0;
            }
        }                
    }
    
    public void newGame()
    {
        initBoard();
        spawnBrick();
        score = 0;
        state = 1;
    }
    
    private void spawnBrick()
    {
        int randomBrick = randomGen.nextInt(7)+1;
        int centerCol = cols/2;
        
        switch(randomBrick)
        {
            case 1:
                fallingBrick = new ElBrick(centerCol);
                break;
            case 2:
                fallingBrick = new EssBrick(centerCol);
                break;
            case 3:
                fallingBrick = new JayBrick(centerCol);
                break;
            case 4:
                fallingBrick = new LongBrick(centerCol);
                break;
            case 5:
                fallingBrick = new SquareBrick(centerCol);
                break;
            case 6:
                fallingBrick = new StackBrick(centerCol);
                break;
            case 7:
                fallingBrick = new ZeeBrick(centerCol);
        }
    }    
  
    public void makeMove(String moveType)
    {
        if(moveType.equals("down"))
        {    
            fallingBrick.moveDown();
            validateMove();
        }    
        if(moveType.equals("left"))
        {
            fallingBrick.moveLeft();
            validateHorizontal("left");            
        }
        if(moveType.equals("right"))
        {
            fallingBrick.moveRight();
            validateHorizontal("right");
        }
    }   
    
    private void validateMove()
    {
        if (validateSegment())
        {
            fallingBrick.moveUp(); 
            transferColor();
            rowChecker();
            spawnBrick();
        }    
        
        if(validateBackground())
        {
            if (validateRow()) 
            {
                if(state == 1)
                {
                    fallingBrick.moveUp();
                    transferColor();
                    rowChecker();
                    spawnBrick();     
                    if(validateBackground())
                        state = 0;
                }
            }
            else state = 0;
            
        }
        endGameChecker();
        
        if(state == 0)
            endGameScoring();
    }
    
    public boolean validateSegment()
    {
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            if (getSegmentRow(segment) > rows-1)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean validateBackground()
    {
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            int blockX = getSegmentCol(segment);
            int blockY = getSegmentRow(segment);

            if (background[blockY][blockX] != 0)
                return true;
        }
        return false;
    }
    
    public boolean validateRow()
    {
        int requiredBricks = 0;
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            if (getSegmentRow(segment) >= 1 || fallingBrick.getColorNum() == 4) 
            {
                requiredBricks++;
            }
        }
        
        if (requiredBricks == fallingBrick.getNumSegments())
            return true;
        else
            return false;
    }
    
    
    private void validateHorizontal(String validationDirection)
    {//Separated from validateMove to prevent unnecessary test and overly-complex method
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            if(getSegmentCol(segment) >= 0)
            {
                if(getSegmentCol(segment) < cols)
                {
                   int rightX = getSegmentCol(segment);
                   int rightY = getSegmentRow(segment);
                   if(background[rightY][rightX] != 0)
                   {
                       if(validationDirection.equals("left"))
                           fallingBrick.moveRight();
                       if(validationDirection.equals("right"))
                           fallingBrick.moveLeft();
                   }                       
               }
               else fallingBrick.moveLeft();
            }    
            else fallingBrick.moveRight();
        }
    }     
    
    private void transferColor()
    {
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            int brickX = getSegmentCol(segment);
            int brickY = getSegmentRow(segment);
            background[brickY][brickX] = getColorIndex();
        }
    }       

    public void endGameChecker()
    {
        for(int col = 0; col < cols;col++)
        {
            if(background[0][col] != 0)
            {
                state = 0;               
            }                
        }
    }
    
    public void rowChecker()
    {
        int clearCounter = 0;
        for (int row = fallingBrick.getMaxRow(); row >= fallingBrick.getMinRow(); row--)
        {
            if(! rowHasSpace(row))
            {
                for (int col = 0; col < cols; col++)
                {
                    background[row][col] = 0;
                }
                clearCounter++;
            }
            
            else
            {
                if(row != fallingBrick.getMaxRow())
                {
                    if(! rowHasColor(row + clearCounter))
                    {
                        rowCopy(row, clearCounter);
                    }
                }
            }                        
        }

        rowCopyAll(clearCounter);      
        scoreCalculator(clearCounter);
    }
    
    public void scoreCalculator(int rowsCleared)
    {
        int oneRowScore = 100;
        int twoRowScore = 300;
        int threeRowScore = 600;
        int fourRowScore = 1200;
        
        switch(rowsCleared)
        {
            case 1:
                score = score + oneRowScore;
                break;
            case 2:
                score = score + twoRowScore;
                break;
            case 3:
                score = score + threeRowScore;
                break;
            case 4:
                score = score + fourRowScore;
                break;
        }
    }
    
    public boolean rowHasSpace(int row)
    {
        for (int col = 0; col < cols; col++)
        {
            if (background[row][col] == 0)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean rowHasColor(int row)
    {
        for (int col = 0; col < cols; col++)
        {
            if (background[row][col] != 0)
            {
                return true;
            }
        }
        return false;
    }
    
    public void rowCopy(int row, int dropCount)
    {
        for (int col = 0; col < cols; col++)
        {
            background[row + dropCount][col] = background[row][col];
            background[row][col] = 0;
        }
    }
    
    public void rowCopyAll(int dropCount)
    {
        for (int row = fallingBrick.getMinRow()-1; row >= 0; row--)
        {
            if(! rowHasColor(row + dropCount))
            {
                rowCopy(row, dropCount);                
            }            
        }   
    }
    
    public void rotateAndCheck()
    {
        fallingBrick.rotate();
        
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            if(getSegmentCol(segment) >= 0)
            {
                if(getSegmentCol(segment) < cols)
                {
                    if (getSegmentRow(segment) < rows-1)
                    {
                        if (getSegmentRow(segment) > 1)
                        {
                            int rotationX = getSegmentCol(segment);
                            int rotationY = getSegmentRow(segment);
                            if(background[rotationY][rotationX] != 0)
                                fallingBrick.unrotate();
                        }   
                    } 
                    else fallingBrick.moveUp();   
                } 
                else fallingBrick.moveLeft();
            } 
            else fallingBrick.moveRight();
        }
    } 
    
    public String toSaveString()
    {
        String saveData = fallingBrick.getColorNum()+" "+fallingBrick.getOrientation()
                          +" "+score;
        
        for(int segment=0; segment < fallingBrick.getNumSegments(); segment++)
        {
            saveData += " "+getSegmentRow(segment)+" "+getSegmentCol(segment);
        }
        
        saveData += "\n";
        
        for(int row = 0; row < background.length;row++)
        {
            for(int col = 0; col < background[0].length;col++)
            {
                saveData += background[row][col]+ " ";
            }
            saveData += "\n";
        }
        return saveData;
    }
    
    public void saveToFile(String fileName)
    {
        File fileConnection = new File (fileName);
        try
        {    
            FileWriter outWriter = new FileWriter(fileConnection);
            outWriter.write(toSaveString());
            outWriter.close();
        }
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, "!!! Write Error !!!", 
                                          "Error occurred during saving to file!", 2);
        } 
    }
    
    public void initFromFile(String fileName)
    {
        File fileConnection = new File(fileName);
        try
        {
            Scanner inScan = new Scanner(fileConnection);        
            background = new int[rows][cols];
            
            int colorNum = inScan.nextInt();
            int orientation = inScan.nextInt();
            score = inScan.nextInt();
            int centerRow1 = inScan.nextInt();
            int centerCol1 = inScan.nextInt();
            int centerRow2 = inScan.nextInt();
            int centerCol2 = inScan.nextInt();
            int centerRow3 = inScan.nextInt();
            int centerCol3 = inScan.nextInt();
            int centerRow4 = inScan.nextInt();
            int centerCol4 = inScan.nextInt();
            
            switch(colorNum)
            {
                case 1:
                    fallingBrick = new ElBrick(centerCol2);
                    break;
                case 2:
                    fallingBrick = new EssBrick(centerCol2);
                    break;
                case 3:
                    fallingBrick = new JayBrick(centerCol2);
                    break;
                case 4:
                    fallingBrick = new LongBrick(centerCol2);
                    break;
                case 5:
                    fallingBrick = new SquareBrick(centerCol2);
                    break;
                case 6:
                    fallingBrick = new StackBrick(centerCol2);
                    break;
                case 7:
                    fallingBrick = new ZeeBrick(centerCol2);
            }
            
            fallingBrick.brickLoader(orientation, centerRow1, centerCol1, 
                                     centerRow2, centerCol2, centerRow3, 
                                     centerCol3, centerRow4, centerCol4);
            
            for(int row = 0; row < rows;row++)
            {
                for(int col = 0; col < cols;col++)
                {
                    background[row][col] = inScan.nextInt();
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "!!! Read Error !!!", 
                                          "Error occurred during retrieve from file!", 2);
        }
    }                    
    
    public void endGameScoring()
    {
        if (state == 0)
        {
            if (score != 0)
            {
                File fileConnection = new File("leaderScore.txt");
                if (fileConnection.exists())
                {
                    try
                    {
                        Scanner inScan = new Scanner(fileConnection);

                        int firstScore = inScan.nextInt();
                        int secondScore = inScan.nextInt();
                        int thirdScore = inScan.nextInt();
                        int fourthScore = inScan.nextInt();
                        int fifthScore = inScan.nextInt();
                        int sixthScore = inScan.nextInt();
                        int seventhScore = inScan.nextInt();
                        int eighthScore = inScan.nextInt();
                        int ninthScore = inScan.nextInt();
                        int tenthScore = inScan.nextInt();                            

                        ArrayList<Integer> scoreList = new ArrayList<>();
                        scoreList.add(firstScore);
                        scoreList.add(secondScore);
                        scoreList.add(thirdScore);
                        scoreList.add(fourthScore);
                        scoreList.add(fifthScore);
                        scoreList.add(sixthScore);
                        scoreList.add(seventhScore);
                        scoreList.add(eighthScore);
                        scoreList.add(ninthScore);
                        scoreList.add(tenthScore);

                        int listSize = scoreList.size();
                        if(score > scoreList.get(listSize - 1))
                        {
                            scoreList.add(0, score);
                            scoreList.remove(listSize);
                        }
                        else
                        {
                            for(int listPosition = 9; listPosition >= 0; listPosition--)
                            {
                                if(score < scoreList.get(listPosition))
                                {
                                    scoreList.add(listPosition+1, score);
                                    scoreList.remove(listSize);
                                    break;
                                }
                            }
                        }
                        
                        String scoreString = "";
                        for(int listPosition = 0; listPosition < 10; listPosition++)
                        {
                            scoreString += scoreList.get(listPosition)+"\n";
                        }
                        
                        FileWriter outWriter = new FileWriter(fileConnection);
                        outWriter.write(scoreString);
                        outWriter.close();
                    }
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, "!!! Read/Write Error !!!", 
                                          "Error occurred during retrieve or writing from file!", 2);
                    }                        
                }
                else
                {
                    String scoreString = score+"\n0\n0\n0\n0\n0\n0\n0\n0\n0";
                    try
                    {    
                        FileWriter outWriter = new FileWriter(fileConnection);
                        outWriter.write(scoreString);
                        outWriter.close();
                    }
                    catch(IOException ioe)
                    {
                        JOptionPane.showMessageDialog(null, "!!! Write Error !!!", 
                                                      "Error occurred during saving to file!", 2);
                    } 
                }                
            }
        } 
    }
    
    public void viewLeaderboard()
    {
        File fileConnection = new File("leaderScore.txt");
        if (fileConnection.exists())
        {
            try
            {
                Scanner inScan = new Scanner(fileConnection);        
                
                int firstScore = inScan.nextInt();
                int secondScore = inScan.nextInt();
                int thirdScore = inScan.nextInt();
                int fourthScore = inScan.nextInt();
                int fifthScore = inScan.nextInt();
                int sixthScore = inScan.nextInt();
                int seventhScore = inScan.nextInt();
                int eighthScore = inScan.nextInt();
                int ninthScore = inScan.nextInt();
                int tenthScore = inScan.nextInt();                
                
                String scoreText  = "Leaderboard Scores:";
                       scoreText += "\nScore #1.   "+firstScore;
                       scoreText += "\nScore #2.   "+secondScore;
                       scoreText += "\nScore #3.   "+thirdScore;
                       scoreText += "\nScore #4.   "+fourthScore;
                       scoreText += "\nScore #5.   "+fifthScore;
                       scoreText += "\nScore #6.   "+sixthScore;
                       scoreText += "\nScore #7.   "+seventhScore;
                       scoreText += "\nScore #8.   "+eighthScore;
                       scoreText += "\nScore #9.   "+ninthScore;
                       scoreText += "\nScore #10. "+tenthScore;
                       
                JOptionPane.showMessageDialog(null, scoreText, "Scores  -  By 'The Logans'", 1);
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "!!! Read Error !!!", 
                                              "Error occurred during retrieve from file!", 2);
            }
        }
        else
        {
            String scoreText  = "Leaderboard Scores:";
                   scoreText += "\nScore #1.   0";
                   scoreText += "\nScore #2.   0";
                   scoreText += "\nScore #3.   0";
                   scoreText += "\nScore #4.   0";
                   scoreText += "\nScore #5.   0";
                   scoreText += "\nScore #6.   0";
                   scoreText += "\nScore #7.   0";
                   scoreText += "\nScore #8.   0";
                   scoreText += "\nScore #9.   0";
                   scoreText += "\nScore #10. 0";
                       
                JOptionPane.showMessageDialog(null, scoreText, "Scores  -  By 'The Logans'", 1);
        }
    }
    
    public void clearLeaderBoard()
    {
        File fileConnection = new File ("leaderScore.txt");
        String scoreString = "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        try
        {    
            FileWriter outWriter = new FileWriter(fileConnection);
            outWriter.write(scoreString);
            outWriter.close();
        }
        catch(IOException ioe)
        {
            JOptionPane.showMessageDialog(null, "!!! Write Error !!!", 
                                          "Error occurred during saving to file!", 2);
        } 
    }
    
    public void resetState()
    {
        state = 1;
    }
    
    public int getSegmentCol(int segment)
    {
        return fallingBrick.getPosition(segment, 0);
    }

    public int getSegmentRow(int segment)
    {
        return fallingBrick.getPosition(segment, 1);
    }

    public int getBackgroundPosition(int row, int col)
    {
        return background[row][col];
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    
    public int getColorIndex()
    {
        return fallingBrick.getColorNum();
    }
    
    public int getSegments()
    {
        return fallingBrick.getNumSegments();
    }     
    
    public int getScore()
    {
        return score;
    }
    
    public int getState()
    {
        return state;
    }                        
}
