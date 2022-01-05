/*
Part of the Tetris Project. Is the super class of the specialized bricks.
Logan Fontenot & Logan Mancilla
11/10/21
 */


public abstract class TetrisBrick {
    int[][] position;
    int orientation = 0;
    int numSegments = 4;
    int colorNum;
    
    public TetrisBrick()
    {
        position = new int[4][2];
    }
    
    public void moveDown()
    {  
       for(int segment=0; segment < numSegments; segment++)
        {
           position[segment][1] = position[segment][1] + 1;
        }
    }
    
    public void moveUp()
    {
        for(int segment=0; segment < numSegments; segment++)
        {
           position[segment][1] = position[segment][1] - 1;
        }
    }
    
    public void moveLeft()
    {
        for(int segment=0; segment < numSegments; segment++)
        {
           position[segment][0] = position[segment][0] - 1;
        }
    }
    
    public void moveRight()
    {
        for(int segment=0; segment < numSegments; segment++)
        {
           position[segment][0] = position[segment][0] + 1;
        }
    }
    
    public void brickLoader(int orientNum, int centerRow1, int centerCol1, 
                            int centerRow2, int centerCol2, int centerRow3, 
                            int centerCol3, int centerRow4, int centerCol4)
    {
        orientation = orientNum - 1;
        if(orientation == 0)
            orientation = 4;
        
        position = new int[][]{{centerCol1, centerRow1},{centerCol2, centerRow2},
                               {centerCol3, centerRow3},{centerCol4, centerRow4}};
        rotate();
    }
    
    public int getMinRow()
    {
        int minRow = position[0][1];
        for(int segment=1; segment < numSegments; segment++)
        {
            if(position[segment][1] < minRow)
                minRow = position[segment][1];
        }
        return minRow;
    }
    
    public int getMaxRow()
    {
        int maxRow = position[0][1];
        for(int segment=1; segment < numSegments; segment++)
        {
            if(position[segment][1] > maxRow)
                maxRow = position[segment][1];
        }
        return maxRow;
    }
    
    public int getNumSegments() {
        return numSegments;
    }

    public int getPosition(int segment, int axis) {
        return position[segment][axis];
    }

    public int getColorNum() {
        return colorNum;
    }
    
    public int getOrientation() {
        return orientation;
    }
    
    public abstract void initPosition(int centerColumn);
    
    public abstract void rotate();
    
    public abstract void unrotate();
}
