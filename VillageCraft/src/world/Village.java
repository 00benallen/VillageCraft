package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import exceptions.CannotDestroyBuildingException;
import exceptions.InvalidLocationException;

public class Village{
	private final int x, y;
	private volatile int sizeRank;
	private int growth = 0;
	private volatile ArrayList<Villager> population;
	private volatile Building[][] buildings;
	private int[] resources = new int[Chunk.NUM_RSRCE_TYPES];
	
	//private int updateCount = 0, drawCount = 0; //prevents multiple updates resulting from multiple chunk occupancies
	
	public static final Color chunkBoundColor = Color.red;
	
	public Village(ArrayList<Villager> population, int x, int y) {
		this.x = x;
		this.y = y;
		this.population = new ArrayList<Villager>();
		this.addPopulation(population);
		constructCity();
	}
	
	public void constructCity()
	{
		buildings = new Building[getSideLength()][getSideLength()];
		
		buildCityHall();
		buildSideWalks();
	}
	
	private void buildCityHall()
	{
		CityHall cityHall = new CityHall();
		Point2D loc = new Point2D.Double((getSideLength()-1)/2, (getSideLength()-1)/2);
		try {
			addBuilding(cityHall, loc);
		} catch (InvalidLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void buildSideWalks()
	{
		int [] dx = {0, 1, 0, -1};
		int [] dy = {-1, 0, 1, 0};

		int x0 = (getSideLength()-1)/2, y0 = (getSideLength()-1)/2;
		for (int d = 0; d < 4; ++d, x0 += dx[d%4], y0 += dy[d%4])
		{
			for (int i = 1; i <= (Chunk.lengthOfChunk-CityHall.height)/2; ++i)
			{
				Point2D loc = new Point2D.Double(x0+i*dx[d], y0+i*dy[d]);
				try {
					addBuilding(new SideWalk(), loc);
				} catch (InvalidLocationException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void update()
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
	
	public BufferedImage draw(int chunkX, int chunkY)
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength(), Chunk.getPixelLength(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D gI = image.createGraphics();

		int x0 = Chunk.lengthOfChunk, y0 = chunkY*Chunk.lengthOfChunk;
		for (int i = x0; i < x0+Chunk.lengthOfChunk; ++i)
		{
			for (int j = y0; j < y0+Chunk.lengthOfChunk; ++j)
			{
				if (buildings[i][j] != null)
				{
					gI.drawImage(buildings[i][j].draw(), (i-x0)*Building.lengthOfBuilding, (j-y0)*Building.lengthOfBuilding, Building.lengthOfBuilding, Building.lengthOfBuilding, null);
				}
			}
		}
		
		gI.setColor(Color.pink);
		gI.fillRect(6*8, 0, 10, 10);
		
		return image;
	}
		
	public void setSizeRank(int population) {
		int newSizeRank = population/10 + 1;
		if (newSizeRank != sizeRank)
		{
			int minSideLength = getSideLength(Math.min(newSizeRank, sizeRank));
			Building[][] newBuildings = new Building[getSideLength(newSizeRank)][getSideLength(newSizeRank)];
			for (int iNew = Math.max(0, newSizeRank-sizeRank)*16, iOld = Math.max(0, sizeRank-newSizeRank)*16; iNew < minSideLength|| iOld < minSideLength; ++iNew, ++iOld)
			{
				for (int jNew = Math.max(0, newSizeRank-sizeRank)*16, jOld = Math.max(0, sizeRank-newSizeRank)*16; jNew < minSideLength|| jOld < minSideLength; ++jNew, ++jOld)
				{
					newBuildings[iNew][jNew] = buildings[iOld][jOld];
				}
			}
			
			growth += newSizeRank - sizeRank;

			buildings = newBuildings;
			sizeRank = newSizeRank;
		}
	}
	
	public void addBuilding(Building newBuilding, Point2D location) throws InvalidLocationException {
		if (!isInVillage(location) || !isInVillage(new Point2D.Double(location.getX()+newBuilding.getWidth(), location.getY()+newBuilding.getHeight())))
			throw new InvalidLocationException();
		
		for (int i = (int) location.getX(); i < location.getX()+newBuilding.getWidth(); ++i)
		{
			for (int j = (int) location.getY(); j < location.getY()+newBuilding.getHeight(); ++j)
			{
				if (this.buildings[i][j] != null)
				{
					throw new InvalidLocationException();
				}
			}
		}
		
		for (int i = (int) location.getX(); i < location.getX()+newBuilding.getWidth(); ++i)
		{
			for (int j = (int) location.getY(); j < location.getY()+newBuilding.getHeight(); ++j)
			{
				this.buildings[i][j] = newBuilding;
			}
		}
	}
	
	public void removeBuilding(Point2D location) throws InvalidLocationException, CannotDestroyBuildingException {
		if (!isInVillage(location))
			throw new InvalidLocationException();
		Building toDestroy = this.buildings[(int)location.getX()][(int)location.getY()];
		if (!isInVillage(new Point2D.Double(location.getX()+toDestroy.getWidth(), location.getY()+toDestroy.getHeight())))
			throw new InvalidLocationException();
		for (int i = (int) location.getX(); i < location.getX()+toDestroy.getWidth(); ++i)
		{
			for (int j = (int) location.getY(); j < location.getY()+toDestroy.getHeight(); ++j)
			{
				if (this.buildings[i][j] == null || this.buildings[i][j].isInfrastructure())
					throw new CannotDestroyBuildingException();
			}
		}

		for (int i = (int) location.getX(); i < location.getX()+toDestroy.getWidth(); ++i)
		{
			for (int j = (int) location.getY(); j < location.getY()+toDestroy.getHeight(); ++j)
			{
				this.buildings[i][j] = null;
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	GETTERS AND SETTERS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	public int getSizeRank() {return this.sizeRank;}
	
	public int getGrowth() {return growth; }
	public void resetGrowth() { growth = 0; }
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	HELPER METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean isInVillage(Point2D p)
	{
		if (p.getX() > getSideLength() || p.getX() < 0 || p.getY() > getSideLength() || p.getY() < 0)
			return false;
		return true;
	}
	
	public Point2D getRelativePixelCenter()
	{
		return new Point2D.Double(getPixelSideLength()/2.0, getPixelSideLength()/2.0);		
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
		return getSideLength(sizeRank)*Building.lengthOfBuilding;
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
	
	public int getNumChunks() {return getNumChunks(this.getSizeRank());}
	public int getNumChunks(int sizeRank)
	{
		return Math.max(0, ((sizeRank*2) - 1)*((sizeRank*2) - 1));
	}

	public int getX() {	return x; }
	public int getY() {	return y; }
}
