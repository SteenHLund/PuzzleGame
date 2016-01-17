import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

//A simple class that creates the block object.  The blocks are the squares in the grid for the game
public class Block {
	private Dimension blockSize;
	private int number;
	private Point pos;
	
	public Block(Dimension size, int value, Point place) {
		blockSize = size;
		number = value;
		pos = place;
	}
	
	//Gets the size of the block
	public Dimension getSize() {
		return blockSize;
	}
	
	//Gets the number on the block
	public int getValue() {
		return number;
	}
	
	//Gets its x and y chordinates
	public Point getPos() {
		return pos;
	}
	
	//Sets the new x and y chordinates
	public void setPos(Point newPos) {
		pos = newPos;
	}
	
	//Gives the square that is formed from the block
	public Rectangle blockInfo() {
		return new Rectangle(pos.x, pos.y, blockSize.width, blockSize.height);
	}
}
