/*
Part of the Tetris Project. Is a subclass that holds the info for a specialized brick.
Logan Fontenot & Logan Mancilla
11/10/21
 */

public class SquareBrick extends TetrisBrick{
    public SquareBrick(int centerColumn)
    {
        initPosition(centerColumn);
        colorNum = 5;
    }
    
    public void initPosition(int centerColumn)
    {
        position = new int[][]{{centerColumn-1, 0},{centerColumn, 0},
                               {centerColumn-1, 1},{centerColumn, 1}};
    }
    
    public void rotate()
    {
        //no rotation needed
    }
    
    public void unrotate()
    {
        //no rotation needed
    }
}
