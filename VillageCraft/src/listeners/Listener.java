package listeners;

import java.awt.event.*;
import java.util.ArrayList;

public class Listener implements KeyListener, MouseListener{
	ArrayList<InputEvent> unprocessedEvents = new ArrayList<InputEvent>();

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
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		unprocessedEvents.add(e);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		unprocessedEvents.add(e);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		unprocessedEvents.add(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		unprocessedEvents.add(e);
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
