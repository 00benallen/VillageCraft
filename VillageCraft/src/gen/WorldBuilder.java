package gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import resources.Villager;
import world.Chunk;
import world.Village;
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
		
		int size = 0;
		Random rand = new Random();
		while(s.hasNextLine()) {
			String line = s.nextLine();
			if(line.charAt(0) == 'S') {
				size = Integer.parseInt(line.substring(2));
			}
			else if(line.charAt(0) == 'V') {
				String[] splitLine = line.split(" ");
				int x = Integer.parseInt(splitLine[1]);
				int y = Integer.parseInt(splitLine[2]);
				//chunks.set((x + y)*size, new Chunk(rand.nextInt(4), true, false ));
			}
			else if(line.charAt(0) == 'P') {
				String[] splitLine = line.split(" ");
				int x = Integer.parseInt(splitLine[1]);
				int y = Integer.parseInt(splitLine[2]);
				//chunks.set((x + y)*size, new Chunk(rand.nextInt(4), true, true));
			}
		}
		
		for(int i = 0; i < size*size; i++) {
			chunks.add(new Chunk(rand.nextInt(4)));
		}
		
		int center = chunks.size()/2;
		ArrayList<Villager> populationCenter = new ArrayList<Villager>();
		populationCenter.add(new Villager());
		chunks.set(center, new Village(chunks.get(center).getBiome(), populationCenter));
		
		World world = new World(chunks);
		return world;
	}

}
