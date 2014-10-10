package resources;

public class Material extends Item {
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//	NOT 100% SURE THIS IS HOW WE SHOULD DO IT, BUT BEFORE YOU CHANGE IT, TALK TO ME
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//*
	//to be used as a sort of pseudo single-instance class maybe?
	private int type;
	private Material(int type)
	{
		this.type = type;
	}
	
	private static final int RSRCE_FOOD = 0, RSRCE_LUMBER = 1;
	public static Material lumber = new Material(RSRCE_LUMBER);
	public static Material food = new Material(RSRCE_FOOD);
	/*/
	public static final int RSRCE_NOTHING = 0, RSRCE_LUMBER = 1;
	public final int type;
	private int quantity;
	public Material(int type, int quantity)
	{
		this.type = type;
		this.quantity = quantity;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	public int getType()
	{
		return type;
	}
	//*/
}
