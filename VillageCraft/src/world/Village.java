package world;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Village extends Chunk{
	private int sizeRank;
	private ArrayList<Villager> population;
	private Building[][] buildings;
	//private int[] resources = new int[Chunk.NUM_RSRCE_TYPES];
	private int updateCount = 0, drawCount = 0; //prevents multiple updates resulting from multiple chunk occupancies
		
	public Village(int biome, ArrayList<Villager> population, int initResources) {
		super(biome, initResources);
		this.population = new ArrayList<Villager>();
		this.addPopulation(population);
		constructCityHall();
	}
	
	private void constructCityHall()
	{
		CityHall cityHall = new CityHall();

		int sideLength = getSideLength(this.getSizeRank());
		buildings = new Building[sideLength][sideLength];
		
		int x = sideLength/2 - 1, y = sideLength/2 - 1;
		buildings[x][y] = cityHall;
		buildings[y][x+1] = cityHall;
		buildings[y+1][x] = cityHall;
		buildings[y+1][x+1] = cityHall;
	}
	
	@Override
	public void update()
	{
		if (updateCount == 0)  //prevents multiple updates resulting from multiple chunk occupancies
		{
			for (int i = 0; i < population.size(); ++i)
			{
				population.get(i).update();
			}
			
			for (int i = 0; i < buildings.length; ++i)
			{
				for (int j = 0; j < buildings[i].length; ++j)
				{
					if (buildings[i][j] != null)
					{
						buildings[i][j].update();
					}
				}
			}
		}
		
		++updateCount;
		if (updateCount == getNumChunks() || getNumChunks() == 0)
		{
			updateCount = 0;
		}
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength(), Chunk.getPixelLength(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		int y0 = (drawCount/getChunkSideLength())*getSideLength(sizeRank);
		int x0 = (drawCount%getChunkSideLength())*getSideLength(sizeRank);
		for (int i = x0; i < x0+getSideLength(sizeRank); ++i)
		{
			for (int j = y0; j < y0+getSideLength(sizeRank); ++j)
			{
				if (buildings[i][j] != null)
				{
					g.drawImage(buildings[i][j].draw(), (i-x0)*buildingLength, (j-y0)*buildingLength, null);
				}
			}
		}
		
		++drawCount;
		if (drawCount == getNumChunks() || getNumChunks() == 0)
		{
			drawCount = 0;
		}
		
		return image;
	}
	
	public void addPopulation(ArrayList<Villager> newPopulation) {
		this.population.addAll(newPopulation);
		setSizeRank(this.population.size());
	}
	
	public void addVillager(Villager villager) {
		this.population.add(villager);
		setSizeRank(this.population.size());
	}
	
	public int getPopulationSize() {return population.size();}
	
	public ArrayList<Villager> getPopulation()
	{
		return population;
	}
	
	//TODO this errors Daniel please fix!
	public void setSizeRank(int population) {
		int newSizeRank = population/10 + 1;
		int minSideLength = getSideLength(Math.min(newSizeRank, sizeRank));
		Building[][] newBuildings = new Building[getSideLength(newSizeRank)][getSideLength(newSizeRank)];
		for (int iNew = Math.max(0, newSizeRank-sizeRank)*16, iOld = Math.max(0, sizeRank-newSizeRank)*16; iNew < minSideLength|| iOld < minSideLength; ++iNew, ++iOld)
		{
			for (int jNew = Math.max(0, newSizeRank-sizeRank)*16, jOld = Math.max(0, sizeRank-newSizeRank)*16; jNew < minSideLength|| jOld < minSideLength; ++jNew, ++jOld)
			{
				newBuildings[iNew][jNew] = buildings[iOld][jOld];
			}			
		}
		buildings = newBuildings;
		sizeRank = newSizeRank;
		
	}
	public int getSizeRank() {return this.sizeRank;}
	
	public boolean addBuilding(Building newBuilding, Point2D location) {
		if (!isInVillage(location))
			return false;
		
		Building oldBuilding = this.buildings[(int)location.getX()][(int)location.getY()];
		if (oldBuilding == null && !(oldBuilding instanceof CityHall))
		{
			this.buildings[(int)location.getX()][(int)location.getY()] = newBuilding;
		}
		
		return true;
	}
	
	public boolean removeBuilding(Point2D location) {
		if ((this.buildings[(int)location.getX()][(int)location.getY()] instanceof CityHall) || !isInVillage(location))
			return false;
		this.buildings[(int)location.getX()][(int)location.getY()] = null;
		return true;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	HELPER METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isInVillage(Point2D p)
	{
		if (p.getX() > getSideLength(this.getSizeRank()) || p.getX() < 0 || p.getY() > getSideLength(this.getSizeRank()) || p.getY() < 0)
			return false;
		return true;
	}
	
	public int getSideLength(int sizeRank)
	{
		return Math.max(0, ((sizeRank*2) - 1)*length);
	}
	
	public int getChunkSideLength()
	{
		return Math.max(0, ((sizeRank*2) - 1));
	}
	
	private int getNumChunks()
	{
		return Math.max(0, ((sizeRank*2) - 1)*((sizeRank*2) - 1));
	}
}
