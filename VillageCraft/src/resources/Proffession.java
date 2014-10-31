package resources;

import world.Chunk;

public class Proffession {
	public static final int PROF_UNEMPLOYED = 0, PROF_LUMBERJACK  = 1;
	private int type;
	private int heldRsrceType;
	private int rsrceQuantity;
	
	public Proffession(int type) {
		this.setType(type);
		this.setHeldRsrceType();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHeldRsrceType() {
		return heldRsrceType;
	}

	private void setHeldRsrceType() {
		switch (this.type)
		{
			case PROF_UNEMPLOYED: {
				this.heldRsrceType = Chunk.RSRCE_NOTHING;
				break;
			}
			case PROF_LUMBERJACK: {
				this.heldRsrceType = Chunk.RSRCE_WOOD;
				break;
			}
		}
	}

	public int getRsrceQuantity() {
		return rsrceQuantity;
	}

	public void setRsrceQuantity(int rsrceQuantity) {
		this.rsrceQuantity = rsrceQuantity;
	}

}
