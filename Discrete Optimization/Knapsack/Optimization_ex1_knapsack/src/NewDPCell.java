import java.util.BitSet;


public class NewDPCell {
	public BitSet taken;
	public NewDPCell(int cellValue,NewDPCell from, boolean isTaken, int index) {
		update(cellValue, from, isTaken, index);
	}

	public void update(NewDPCell from)
	{
		cellValue = from.cellValue;
		if(from.taken!=null)
		{
			taken = (BitSet)from.taken.clone();
		}
	}

	public void update(int cellValue, NewDPCell from, boolean isTaken,
			int index) {
		
		this.cellValue = cellValue;
		
		if(index == -1)
		{
			return;
		}
		
		if(from.taken == null)
		{
			taken = new BitSet();
		}
		else
		{
			taken = (BitSet)from.taken.clone();
		}
		taken.set(index,isTaken);
	}
	
	

	public int cellValue;

}
