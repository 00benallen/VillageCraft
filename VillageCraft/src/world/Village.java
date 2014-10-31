package world;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Render;

public class Village extends Chunk{
	private volatile int sizeRank;
	private volatile ArrayList<Villager> population;
	private volatile Building[][] buildings;
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

		int sideLength = getSideLength();
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
		if (updateCount == getNumChunks()-1 || getNumChunks() == 0)
		{
			updateCount = 0;
		}
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength()+1, Chunk.getPixelLength()+1, BufferedImage.TYPE_INT_ARGB);
		Graphics gI = image.getGraphics();
		
		if (Render.drawChunkBoundaries)
		{
			gI.setColor(Color.red);
			gI.drawRect(0, 0, Chunk.getPixelLength(), Chunk.getPixelLength());
		}
		
		int chunkX = drawCount%getChunkSideLength(), chunkY = drawCount/getChunkSideLength();
		int x0 = (chunkX)*Chunk.lengthOfChunk, y0 = (chunkY)*Chunk.lengthOfChunk;
		for (int i = x0; i < x0+Chunk.lengthOfChunk; ++i)
		{
			for (int j = y0; j < y0+Chunk.lengthOfChunk; ++j)
			{
				if (buildings[i][j] != null)
				{
					gI.drawImage(buildings[i][j].draw(), (i-x0)*Chunk.lengthOfBuilding, (j-y0)*Chunk.lengthOfBuilding, Chunk.lengthOfBuilding, Chunk.lengthOfBuilding, null);
					//gI.setColor(Color.orange);
					//gI.fillRect((i-x0)*Chunk.lengthOfBuilding, (j-y0)*Chunk.lengthOfBuilding, 6, 6);
				}
			}
		}
		
		++drawCount;
		if (drawCount >= getNumChunks())
		{
			drawCount -= getNumChunks();
		}
		else if (getNumChunks() == 0)
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
		if (p.getX() > getSideLength() || p.getX() < 0 || p.getY() > getSideLength() || p.getY() < 0)
			return false;
		return true;
	}
	
	public Point2D getRelativeCenter()
	{
		return new Point2D.Double(getSideLength()/2.0, getSideLength()/2.0);
	}
	
	public Point2D getRelativeChunkCenter()
	{
		return new Point2D.Double(getChunkSideLength()/2.0, getChunkSideLength()/2.0);
	}
	
	public int getPixelSideLength() {return getPixelSideLength(this.getSizeRank());}
	public int getPixelSideLength(int sizeRank)
	{
		return getSideLength(sizeRank)*Chunk.lengthOfBuilding;
	}
	
	public int getSideLength() {return getSideLength(this.getSizeRank());}
	public int getSideLength(int sizeRank)
	{
		return getChunkSideLength(sizeRank)*Chunk.lengthOfChunk;
	}
	
	public int getChunkSideLength() {return getChunkSideLength(this.getSizeRank());}
	public int getChunkSideLength(int sizeRank)
	{
		return Math.max(0, ((sizeRank*2) - 1));
	}
	
	private int getNumChunks() {return getNumChunks(this.getSizeRank());}
	private int getNumChunks(int sizeRank)
	{
		return Math.max(0, ((sizeRank*2) - 1)*((sizeRank*2) - 1));
	}
}
