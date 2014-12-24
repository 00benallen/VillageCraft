package listeners;

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Iterable<InputEvent>{
	private volatile Queue<InputEvent> unprocessedEvents = new ArrayDeque<InputEvent>();
	private volatile Point2D mouse = new Point2D.Double(-1, -1);
	
	public static final Listener universal = new Listener();

	public Point2D getMouse()
	{
		return (Point2D) mouse.clone();
	}
	
	public InputEvent peekAtEvent()
	{
		return unprocessedEvents.peek();
	}
	public InputEvent removeEvent()
	{
		return unprocessedEvents.remove(); 
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		unprocessedEvents.add(e);
		mouse = e.getPoint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		unprocessedEvents.add(e);
		mouse = e.getPoint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		unprocessedEvents.add(e);
		mouse = new Point2D.Double(-1, -1);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		unprocessedEvents.add(e);
		mouse = e.getPoint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		unprocessedEvents.add(e);
		mouse = e.getPoint();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		unprocessedEvents.add(e);
		mouse = e.getPoint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouse = e.getPoint();
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		unprocessedEvents.add(e);
		mouse = e.getPoint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		unprocessedEvents.add(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		unprocessedEvents.add(e);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		unprocessedEvents.add(e);
	}

	
	@Override
	public Iterator<InputEvent> iterator() {
		return unprocessedEvents.iterator();
	}
}
