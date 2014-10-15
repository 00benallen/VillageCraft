package resources;

/**
 * 
 * @deprecated 
 *
 */
public class Material extends Item {

	public static final int RSRCE_ANYHING = -1, RSRCE_NOTHING = 0, RSRCE_LUMBER = 1;
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
