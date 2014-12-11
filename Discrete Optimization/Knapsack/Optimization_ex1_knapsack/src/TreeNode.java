public class TreeNode
{
	public double optimalRelaxaition;
	public int value;
	public int roomLeft;
	public int index;
	public TreeNode withNext;
	public TreeNode withoutNext;
	public TreeNode parent;

	public TreeNode(int index ,int value,int roomLeft,double optimalRelaxaition,TreeNode parent)
	{
		this.index = index;
		this.optimalRelaxaition = optimalRelaxaition;
		this.value = value;
		this.roomLeft = roomLeft;
		this.parent = parent;
	}
}