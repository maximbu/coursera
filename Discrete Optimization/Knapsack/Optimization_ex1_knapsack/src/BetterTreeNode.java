import java.util.BitSet;

public class BetterTreeNode
{
	public double opt;
	public int value;
	public int roomLeft;
	public int index;
	public BitSet taken;
	

	public BetterTreeNode(int index ,int value,int roomLeft,double optimalRelaxaition,BetterTreeNode parent,boolean isTaken)
	{
		this.index = index;
		this.opt = optimalRelaxaition;
		this.value = value;
		this.roomLeft = roomLeft;
		
		if(parent == null)
			return;
		
		if(parent.taken == null)
		{
			taken = new BitSet();
		}
		else
		{
			taken = (BitSet)parent.taken.clone();
		}
		
		taken.set(index,isTaken);
	}
	
	public BetterTreeNode(BetterTreeNode node)
	{
		this.index = node.index;
		this.opt = node.opt;
		this.value = node.value;
		this.roomLeft = node.roomLeft;
		this.taken = node.taken;
	}
	
	
}
