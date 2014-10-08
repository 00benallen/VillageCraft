package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GraphicsMain {
	private static JFrame frame;
	public static final int WIDTH = 1024, HEIGHT = 768;
	
	public static void init() {
		frame = new JFrame();
	}

	public static void start() {
		 frame = new JFrame("VillageCraft");
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	     frame.setResizable(false);
	     frame.pack();
	     frame.setVisible(true);
	}

}
