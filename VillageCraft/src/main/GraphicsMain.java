package main;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JFrame;

import listeners.Listener;

public class GraphicsMain {
	private static JFrame frame;
	public static final int WIDTH = 1024, HEIGHT = 768;
	public static Graphics2D g;

	public static void init() {
		frame = new JFrame("VillageCraft");
		frame.addKeyListener(Listener.universal);
		frame.addMouseListener(Listener.universal);
		frame.addMouseMotionListener(Listener.universal);
		frame.addMouseWheelListener(Listener.universal);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		g = (Graphics2D) frame.getContentPane().getGraphics();
	}

	public static Graphics2D getGraphics() {
		return g;
	}
}
