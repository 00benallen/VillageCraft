package gen;

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
	public static World generateWorld(String fileName) {
		
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
			chunks.add(new Chunk(rand.nextInt(4), 0));
		}
		
		int center = chunks.size()/2;
		ArrayList<Villager> populationCenter = new ArrayList<Villager>();
		populationCenter.add(new Villager());
		chunks.set(center, new Village(chunks.get(center).getBiome(), populationCenter, 0));
		
		World world = new World(chunks);
		s.close();
		return world;
	}

}
