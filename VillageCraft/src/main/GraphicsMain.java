package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;

import listeners.Listener;

public class GraphicsMain {
	private static JFrame frame;
	public static final int WIDTH = 1024, HEIGHT = 768;
	public static Graphics2D g;
	public static final Listener listener = new Listener();
	
	public static void init() {
		frame = new JFrame();
	}

	public static void start() {
		 frame = new JFrame("VillageCraft");
		 frame.addKeyListener(listener);
		 frame.addMouseListener(listener);
		 frame.addMouseMotionListener(listener);
	     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	     frame.setResizable(false);
	     frame.pack();
	     frame.setVisible(true);
	     g = (Graphics2D) frame.getGraphics();
	}
	
	public static Graphics2D getGraphics() {
		return g;
	}
}
