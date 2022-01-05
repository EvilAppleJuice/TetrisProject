/*
Part of the Tetris Project. Is a subclass that holds the info for a specialized brick.
Logan Fontenot & Logan Mancilla
11/10/21
 */

public class LongBrick extends TetrisBrick{
   public LongBrick(int centerColumn)
    {
        initPosition(centerColumn);
        colorNum = 4;
    }
    
    public void initPosition(int centerColumn)
    {
        position = new int[][]{{centerColumn - 2, 0},{centerColumn - 1, 0},
                               {centerColumn    , 0},{centerColumn + 1, 0}};      
    } 
    
    public void rotate()
    {
        int centerColumn = position[1][0];
        int centerRow = position[1][1];
        
        orientation++;
        if(orientation > 4)
            orientation = 1;  
        
        switch(orientation) 
        {
            case 1:
                position = new int[][]{{centerColumn, centerRow - 1},{centerColumn, centerRow    },
                                       {centerColumn, centerRow + 1},{centerColumn, centerRow + 2}};
                break;
            case 2:   
                position = new int[][]{{centerColumn - 1, centerRow},{centerColumn    , centerRow},
                                       {centerColumn + 1, centerRow},{centerColumn + 2, centerRow}};
                break;
            case 3:     
                position = new int[][]{{centerColumn, centerRow - 1},{centerColumn, centerRow    },
                                       {centerColumn, centerRow + 1},{centerColumn, centerRow + 2}};
                break;
            case 4:
                position = new int[][]{{centerColumn - 1, centerRow},{centerColumn    , centerRow},
                                       {centerColumn + 1, centerRow},{centerColumn + 2, centerRow}};
                break;
        }       
    }
    
    public void unrotate()
    {
        int centerColumn = position[1][0];
        int centerRow = position[1][1];
        
        orientation--;
        if(orientation < 4)
            orientation = 4;
        
        switch(orientation)
        {
            case 1:
                position = new int[][]{{centerColumn, centerRow - 1},{centerColumn, centerRow    },
                                       {centerColumn, centerRow + 1},{centerColumn, centerRow + 2}};
                break;
            case 2:   
                position = new int[][]{{centerColumn - 1, centerRow},{centerColumn    , centerRow},
                                       {centerColumn + 1, centerRow},{centerColumn + 2, centerRow}};
                break;
            case 3:     
                position = new int[][]{{centerColumn, centerRow - 1},{centerColumn, centerRow    },
                                       {centerColumn, centerRow + 1},{centerColumn, centerRow + 2}};
                break;
            case 4:
                position = new int[][]{{centerColumn - 1, centerRow},{centerColumn    , centerRow},
                                       {centerColumn + 1, centerRow},{centerColumn + 2, centerRow}};
                break;
        }
    }
}
