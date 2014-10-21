package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class World implements ScreenComponent{
	
	private ArrayList<Chunk> chunks;	
	
	public World(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}
	
	public void update() {
		for (int i = 0; i < chunks.size(); ++i)
		{
			getChunk(i).update();
		}
	}
	
	@Override
	public BufferedImage draw()
	{
		BufferedImage image = new BufferedImage(Chunk.getPixelLength(), Chunk.getPixelLength(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		for (int i = 0; i < chunks.size(); ++i)
		{
			Chunk c = chunks.get(i);
			BufferedImage cI = c.draw();
			g.drawImage(cI, (i%getSize())*Chunk.getPixelLength(), (i/getSize())*Chunk.getPixelLength(), null);
		}
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

	public int getSize() {
		return (int)Math.sqrt(chunks.size());
	}
}
