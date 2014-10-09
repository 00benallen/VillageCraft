package listeners;

import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Listener implements KeyListener, MouseListener, MouseMotionListener{
	ArrayList<InputEvent> unprocessedEvents = new ArrayList<InputEvent>();
	Point2D mouse = new Point2D.Double(-1, -1);

	public Point2D getMouse()
	{
		return (Point2D) mouse.clone();
	}
	
	public InputEvent getEvent(int index)
	{
		InputEvent e = unprocessedEvents.remove(index); 
		return e;
	}
	public InputEvent peekAtEvent(int index)
	{
		return unprocessedEvents.get(index);
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
}
