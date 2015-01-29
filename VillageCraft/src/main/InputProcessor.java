package main;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import listeners.Listener;

public class InputProcessor {
	protected void processInput()
	{
		for(InputEvent e : Listener.universal)
		{
			if (e instanceof KeyEvent)
			{
				processEvent((KeyEvent) e);
			}
			else if (e instanceof MouseEvent)
			{
				processEvent((MouseEvent) e);
			}
			
			e.consume();
			Listener.universal.removeEvent();
		}
	}
	
	private void processEvent(KeyEvent e)
	{
		if (e.getID() != KeyEvent.KEY_RELEASED)
		{
			int kc = e.getKeyCode();
			if (kc == KeyEvent.VK_EQUALS || kc == KeyEvent.VK_PLUS)
			{
				Render.zoom(1);
			}
			else if (kc == KeyEvent.VK_MINUS)
			{
				Render.zoom(-1);
			}
			else if (isArrowKey(kc) || isWASD(kc))
			{
				/*
				int [] dx = {0, -1, 0, 1};
				int [] dy = {1, 0, -1, 0};
				int x = dx[kc%4];
				int y = dy[kc%4];
				/*/

			/*	KeyCodes
				0101000   %4  = 00 = down
				0100101   %4  = 01 = left
				0100110   %4  = 10 = up
				0100111   %4  = 11 = right
				
				1010011 /2 %4 = 01 = s
				1000001 /2 %4 = 00 = a
				1010111 /2 %4 = 11 = w
				1000100 /2 %4 = 10 = d		*/
				
				int x, y;
				if (isArrowKey(kc))
				{
					//{0, -1, 0, 1}
					x = (kc&1)*((kc&2) - 1);
					//x = ((kc&3)-2)%2;
					//{1, 0, -1, 0}
					y = ((kc&1) - 1)*((kc&2) - 1);
				}
				else
				{
					//{-1, 0, 1, 0}
					x = (((kc>>1)&3)-1)&-0x80000001;
					//{0, 1, 0, -1}
					y = (2-((kc>>1)&3))&-0x80000001;
				}

				Rectangle2D screen = Render.getScreen();
				Point2D shift = new Point2D.Double(screen.getWidth()*x/8, screen.getHeight()*y/8);
				
				Render.pan(shift);
			}
		}
	}
	private void processEvent(MouseEvent e)
	{
		if (e instanceof MouseWheelEvent)
		{
			Rectangle2D screen = Render.getScreen();
			double pixelRatio = screen.getHeight()/GraphicsMain.HEIGHT;
			Point p = new Point((int) (e.getPoint().x*pixelRatio+screen.getMinX()), (int) (e.getPoint().y*pixelRatio+screen.getMinY()));
			Render.zoom(((MouseWheelEvent) e).getWheelRotation(), p);
		}
	}
	
	public boolean isArrowKey(int keyCode)
	{
		return keyCode >= KeyEvent.VK_LEFT && keyCode <= KeyEvent.VK_DOWN;
	}
	public boolean isWASD(int keyCode)
	{
		return keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_D;
	}
}
