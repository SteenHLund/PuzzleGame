import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Block {
	private Dimension blockSize;
	private int number;
	private Point pos;
	
	public Block(Dimension size, int value, Point place) {
		blockSize = size;
		number = value;
		pos = place;
	}
	
	public Dimension getSize() {
		return blockSize;
	}
	
	public int getValue() {
		return number;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public void setPos(Point newPos) {
		pos = newPos;
	}
	
	public Rectangle blockInfo() {
		return new Rectangle(pos.x, pos.y, blockSize.width, blockSize.height);
	}
}
