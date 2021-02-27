
//controls what part of the world we are looking at for drawing only what we are able to see



final class Viewport
{
   private int row;
   private int col;
   private final int numRows;
   private final int numCols;

   public Viewport(int numRows, int numCols)
   {
      this.numRows = numRows;
      this.numCols = numCols;
   }

   public void shift(int col, int row)
   {
      this.col = col;
      this.row = row;
   }

   public boolean contains(Point p)
   {
      return p.y >= this.getRow() && p.y < this.getRow() + this.getNumRows() &&
         p.x >= this.getCol() && p.x < this.getCol() + this.getNumCols();
   }

   public Point viewportToWorld(int col, int row)
   {
      return new Point(col + this.col, row + this.row);
   }

   public Point worldToViewport(int col, int row)
   {
      return new Point(col - this.col, row - this.row);
   }

   public int getRow() { return row; }
   public int getCol() { return col; }
   public int getNumRows() { return numRows; }
   public int getNumCols() { return numCols; }
}
