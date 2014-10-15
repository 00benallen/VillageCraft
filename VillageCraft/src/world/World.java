package world;

import java.util.ArrayList;

public class World {
	
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
