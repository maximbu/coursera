
public class WeightedData implements Comparable<WeightedData> {
  private int ind;
  private double calc;
  private double taken;
  private int weight;
  private int value;

  public WeightedData(int ind , int weight , int value)
  {
	  this.ind = ind;
	  this.weight = weight;
	  this.value= value; 
	  
	  if(weight!=0)
	  {
		  calc = value/(double)weight;
	  }
  }

  public int getIndex() {
    return ind;
  }
  public double getCalculatedValue() {
    return calc;
  }
  public int getWeight() {
	    return weight;
	  }
  
  public int getValue() {
	    return value;
	  }
  public void setTaken(double val)
  {
	  taken = val;
  }
  
  public double getTaken()
  {
	  return taken;
  }

  public int compareTo(WeightedData other) {
    if (this.getCalculatedValue() == other.getCalculatedValue()) {
      return this.getWeight() - other.getWeight();
    } 
    return (this.getCalculatedValue() > other.getCalculatedValue()?1:-1);
    
  }

}