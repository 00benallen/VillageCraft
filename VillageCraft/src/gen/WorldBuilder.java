package gen;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import main.Main;
import world.Chunk;
import world.Village;
import world.Villager;
import world.World;

public class WorldBuilder {
	private final String fileName;
	public WorldBuilder(String fileName)
	{
		this.fileName = fileName;
	}
	
	public ArrayList<Chunk> generateWorld()
	{	
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		
		String line = s.nextLine();
		int size = Integer.parseInt(line.substring(2));
		Random rand = new Random();
		for(int j = 0; j < size*size; j++) {
			chunks.add(new Chunk(rand.nextInt(4), 0, j%size, j/size));
		}
		
		int center = (int) Math.sqrt(chunks.size())/2;
		ArrayList<Villager> populationCenter = new ArrayList<Villager>();
		populationCenter.add(new Villager());
		Village village = new Village(populationCenter, center, center);
		chunks.get(chunks.size()/2).addVillage(village, new Point2D.Double());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		populationCenter.add(new Villager());
		village.addPopulation(populationCenter);
		
		s.close();
		return chunks;
	}

}
