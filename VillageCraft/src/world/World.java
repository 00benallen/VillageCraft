package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class World implements ScreenComponent{
	
	private volatile ArrayList<Chunk> chunks;	
	
	public World(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}
	
	public void update() {
		updateChunkPointers();
		for (int i = 0; i < chunks.size(); ++i)
		{
			getChunk(i).update();
		}
	}
	
	public void updateChunkPointers()
	{
		ArrayList<Village> villages = getVillagesWithNewSize();
		for (int i = 0; i < chunks.size(); ++i)
		{
			if (villages.contains(chunks.get(i)) && chunks.get(i) instanceof Village)
			{
				//TODO deal with edge cases, where villages are not entirely on-screen/on-map/loaded
				int x = i%this.getSize(), y = i/this.getSize();
				int x2 = x, y2 = y;
				while(chunks.get(x2+1+this.getSize()*(y2+1)) == chunks.get(x2+this.getSize()*y2))
				{
					++x2;
					++y2;
				}
				x = (x+x2)/2;
				y = (y+y2)/2;
				Village v = (Village)chunks.get(i);
				int rx = (int)(v.getRelativeChunkCenter().getX());
				int ry = (int)(v.getRelativeChunkCenter().getY());
				for (int dx = (int) (-1*v.getRelativeChunkCenter().getX()); dx <= rx; ++dx)
				{
					for (int dy = (int) (-1*v.getRelativeChunkCenter().getY()); dy <= ry; ++dy)
					{
						chunks.set(x+dx+this.getSize()*(y+dy), v);
					}
				}
				villages.remove(v);
			}
		}
	}
	
	public ArrayList<Village> getVillagesWithNewSize()
	{
		ArrayList<Integer> villageSizes = getVillageSizesOnMap();
		ArrayList<Village> villages = getVillages();
		for (int i = 0; i < villages.size(); ++i)
		{
			if (villages.get(i).getSizeRank() == villageSizes.get(i))
			{
				villages.remove(i);
				villageSizes.remove(i);
				--i;
			}
		}
		return villages;
	}
	
	private ArrayList<Integer> getVillageSizesOnMap()
	{
		ArrayList<Village> villages = getVillages();
		ArrayList<Integer> villageSizes = new ArrayList<Integer>();
		for (int i = 0; i < villages.size(); ++i)
		{
			villageSizes.add(0);
		}
		for (int i = 0; i < chunks.size(); ++i)
		{
			if (chunks.get(i) instanceof Village)
			{
				int ind = villages.indexOf(chunks.get(i));
				villageSizes.set(ind, villageSizes.get(ind)+1);
			}
		}
		return villageSizes;
	}
	
	public ArrayList<Village> getVillages()
	{
		ArrayList<Village> villages = new ArrayList<Village>();
		for (int i = 0; i < chunks.size(); ++i)
		{
			if (chunks.get(i) instanceof Village)
			{
				if (!villages.contains(chunks.get(i)))
				{
					villages.add((Village) chunks.get(i));
				}
			}
		}
		return villages;
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength()*getSize()+1, Chunk.getPixelLength()*getSize()+1, BufferedImage.TYPE_INT_ARGB);
		Graphics gI = image.getGraphics();
		for (int i = 0; i < chunks.size(); ++i)
		{
			Chunk c = chunks.get(i);
			BufferedImage cI = c.draw();
			gI.drawImage(cI, (i%getSize())*Chunk.getPixelLength(), (i/getSize())*Chunk.getPixelLength(), Chunk.getPixelLength(), Chunk.getPixelLength(), null);
		}
		ArrayList<Village> villages = getVillages();
		for (int i = 0; i < chunks.size(); ++i)
		{
			if (villages.contains(chunks.get(i)) && chunks.get(i) instanceof Village)
			{
				Village c = (Village)chunks.get(i);
				for (Villager v : ((Village) c).getPopulation())
				{
					
					double vAbsX = v.getRelativeX()+c.getRelativeCenter().getX()+(i%getSize())*Chunk.lengthOfChunk, vAbsY = v.getRelativeY()+c.getRelativeCenter().getY()+(i/getSize())*Chunk.lengthOfChunk;
					gI.drawImage(v.draw(), (int)(vAbsX*Chunk.lengthOfBuilding), (int)(vAbsY*Chunk.lengthOfBuilding), null);
					//TODO will draw villager's multiple times right now
				}
				villages.remove(c);
			}
		}
		gI.dispose();
		return image;
	}
	
	public Chunk getChunk(int index) {
		return chunks.get(index);
	}

	public void addChunk(Chunk chunk) {
		this.chunks.add(chunk);
	}
	
	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}

	/**
	 * @return the size of the world, in chunks
	 */
	public int getSize() {
		return (int)Math.sqrt(chunks.size());
	}
}
