import java.util.Arrays;
import java.util.List;


 public class KnapsackBandB {
	 
	 private  int   maxWeight = 0; //maximum weight of knapsack
	 private  int[] w;             //weight of each item
	 private  int[] v;             //value of each item
	 private  int[] maxSet;        //set of optimum items
	 private  int   maxValue;      //value of maximum set
	 
	 public KnapsackBandB(int items ,int[] weights,int[] values,int capacity)
	 {
		 maxWeight = capacity;
		 w = Arrays.copyOf(weights, items);
		 v = Arrays.copyOf(values, items);
	 }
       
	 public void solve()
	 {
		 preProcessing();
		 initialization();
		 reduceAndExpand();
	 }
	 
	 public int getMaxValue()
	 {
		 return maxValue;
	 }
	 
	 private void preProcessing()
	 {
	     /**
	      * Sorting using Selection Sort to Arrange
	      * the data by it's ratio (value/weight)
	      */
	     for (int i=0;i<w.length-1;i++)
	     {
	         int minIndex = i;
	         for (int j=i+1;j<w.length;j++)
	         {
	             if ((double)v[j]/w[j] < (double)v[minIndex]/w[minIndex])
	             {
	                 minIndex = j;
	             }
	  
	         }
	         int temp    = w[i];
	         w[i]        = w[minIndex];
	         w[minIndex] = temp;
	  
	         temp        = v[i];
	         v[i]        = v[minIndex];
	         v[minIndex] = temp;
	     }
	 }
	 
	 private  void initialization()
	 {
	     /**
	      * Make a greedy choosing
	      * Take as much as first item, then second then continue...
	      */
	     int[] combination = new int[w.length];
	     for (int i=0;i<w.length;i++)
	     {
	         int maximum = maxWeight/w[i];
	         combination[i] = maximum;
	         maxWeight = maxWeight - (maximum*w[i]);
	     }
	  
	     /**
	      * Assume the greedy choice taken is maximum set
	      */
	     maxSet = new int[combination.length];
	     copyAllArrayValueTo(combination, maxSet);
	  
	     /**
	      * Calculate the value of maximum set taken
	      */
	     int currentValue = 0;
	     for (int i=0;i<maxSet.length;i++)
	     {
	         currentValue = currentValue + (maxSet[i]*v[i]);
	     }
	     maxValue = currentValue;
	 }
	 
	 private static void copyAllArrayValueTo(int[] source, int[] dest)
	 {
	     for (int i=0;i<source.length;i++) dest[i] = source[i];
	 }
	 
	 private  void reduceAndExpand()
	 {
	     /**
	      * Make a copy of maximum set to be reduced and expanded
	      */
	     int[] set = new int[maxSet.length];
	     copyAllArrayValueTo(maxSet, set);
	  
	     /**
	      * Reduction and Expansion Step Loop Here
	      * Loop until the all of the set is 0
	      */
	     boolean flag = true;
	     while (flag)
	     {
	         /**
	          * Reduction Move
	          * Seek the least significant (not zero) digit
	          */
	         int leastSignificant = set.length-1;
	         for (int i=leastSignificant;i>=0;i--)
	         {
	             if (set[i] != 0)
	             {
	                 leastSignificant = i;
	                 break;
	             }
	         }
	  
	         /**
	          * Reduction Move
	          * Decrement the least significant digit or
	          * set it to 0 (zero) if it's on last position
	          */
	         if (leastSignificant == set.length-1)
	         {
	             maxWeight = maxWeight + (set[leastSignificant]*w[leastSignificant]);
	             set[leastSignificant] = 0;
	         }
	         else
	         {
	             maxWeight = maxWeight + w[leastSignificant];
	             set[leastSignificant]--;
	  
	             /**
	              * Expansion Move
	              * Take a greedy choosing for items on right of
	              * least significant digit
	              */
	             for (int i=leastSignificant+1;i<set.length;i++)
	             {
	                 int maximum = maxWeight/w[i];
	                 set[i] = maximum;
	                 maxWeight = maxWeight - (maximum*w[i]);
	             }
	  
	             /**
	              * Calculating the value of new set
	              */
	             int currentValue = 0;
	             for (int i=0;i<set.length;i++)
	             {
	                 currentValue = currentValue + (set[i]*v[i]);
	             }
	  
	             /**
	              * Replace the optimum set if
	              * the new set better
	              */
	             if (currentValue > maxValue)
	             {
	                 maxValue = currentValue;
	                 copyAllArrayValueTo(set, maxSet);
	             }
	  
	         }
	  
	         /**
	          * Loop terminator
	          * Check if the set is 0
	          */
	         int itemCount = 0;
	         for (int i=0;i<w.length;i++)
	         {
	             itemCount = itemCount + set[i];
	         }
	         if (itemCount == 0) flag = false;
	     }
	 }
       
 }