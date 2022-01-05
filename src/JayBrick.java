/*
Part of the Tetris Project. Is a subclass that holds the info for a specialized brick.
Logan Fontenot & Logan Mancilla
11/10/21
 */

public class JayBrick extends TetrisBrick{
    public JayBrick(int centerColumn)
    {
        initPosition(centerColumn);
        colorNum = 3;
    }
    
    public void initPosition(int centerColumn)
    {
        position = new int[][]{{centerColumn-1, 0},{centerColumn, 0},
                               {centerColumn+1, 0},{centerColumn+1, 1}};
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
                position = new int[][]{{centerColumn, centerRow + 1},{centerColumn    , centerRow    },
                                       {centerColumn, centerRow - 1},{centerColumn - 1, centerRow + 1}};
                break;
            case 2:   
                position = new int[][]{{centerColumn + 1, centerRow},{centerColumn    , centerRow     },
                                       {centerColumn - 1, centerRow},{centerColumn - 1, centerRow - 1}};
                break;
            case 3:     
                position = new int[][]{{centerColumn, centerRow - 1},{centerColumn    , centerRow    },
                                       {centerColumn, centerRow + 1},{centerColumn + 1, centerRow - 1}};
                break;
            case 4:
                position = new int[][]{{centerColumn - 1, centerRow},{centerColumn    , centerRow    },
                                       {centerColumn + 1, centerRow},{centerColumn + 1, centerRow + 1}}; 
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
                position = new int[][]{{centerColumn, centerRow + 1},{centerColumn    , centerRow    },
                                       {centerColumn, centerRow - 1},{centerColumn - 1, centerRow + 1}};
                break;
            case 2:   
                position = new int[][]{{centerColumn + 1, centerRow},{centerColumn    , centerRow     },
                                       {centerColumn - 1, centerRow},{centerColumn - 1, centerRow - 1}};
                break;
            case 3:     
                position = new int[][]{{centerColumn, centerRow - 1},{centerColumn    , centerRow    },
                                       {centerColumn, centerRow + 1},{centerColumn + 1, centerRow - 1}};
                break;
            case 4:
                position = new int[][]{{centerColumn - 1, centerRow},{centerColumn    , centerRow    },
                                       {centerColumn + 1, centerRow},{centerColumn + 1, centerRow + 1}}; 
                break;
        }
    }
}
