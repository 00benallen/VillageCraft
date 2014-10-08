package resources;

import java.util.ArrayList;

public class World {
	
	private ArrayList<Chunk> chunks;
	private int size;
	
	
	public World(int size) {
		chunks = new ArrayList<Chunk>();
		this.setSize(size);
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
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
