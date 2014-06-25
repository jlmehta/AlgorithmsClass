package classProblems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/**
 * Problem: The goal of this problem is to implement the "Median Maintenance" algorithm (covered in the Week 5 lecture on heap 
 * applications). The text file contains a list of the integers from 1 to 10000 in unsorted order; you should treat this as a 
 * stream of numbers, arriving one by one. Letting xi denote the ith number of the file, the kth median mk is defined as the 
 * median of the numbers x1,…,xk. (So, if k is odd, then mk is ((k+1)/2)th smallest number among x1,…,xk; if k is even, then 
 * mk is the (k/2)th smallest number among x1,…,xk.)
 * 
 * In the box below you should type the sum of these 10000 medians, modulo 10000 (i.e., only the last 4 digits). That is, you 
 * should compute (m1+m2+m3+...+m10000)mod10000.
 * 
 * @author JLMehta
 */

public class MedianMaintainance {
	
	private static String inputFile = new File("").getAbsolutePath() + "/bin/inputFiles/Median.txt";
	public static void main(String[] args) 
	{
		MinHeap highHeap = new MinHeap();
		MaxHeap lowHeap = new MaxHeap();
		int medianSum = 0;
		int lineNo = 0;
		
		try {
	        BufferedReader br = new BufferedReader(new FileReader(inputFile));
	        
	        {
	        // To avoid unnecessary checks inside the while loop
	        	int val1 = Integer.parseInt(br.readLine()); System.out.println(val1);
	        	int val2 = Integer.parseInt(br.readLine()); System.out.println(val2);
	        	if(val1<val2)
	        	{
	        		lowHeap.push(val1);
	        		highHeap.push(val2);
	        	}
	        	else
	        	{
	        		lowHeap.push(val2);
	        		highHeap.push(val1);
	        	}
	        	medianSum = val1 + val2;
	        }
	        
	        String line = br.readLine();
	        lineNo = 3;
	        while (line != null) 
	        {
	            int val = Integer.parseInt(line);
//	            System.out.print(val);
	            if(val<lowHeap.peek())
	            	lowHeap.push(val);
	            else 
	            	highHeap.push(val);
	            
	            if(lowHeap.size() - highHeap.size() >1)
	            {
	            	highHeap.push(lowHeap.extractMax());
	            }
	            else if(highHeap.size() - lowHeap.size() >1)
	            {
	            	lowHeap.push(highHeap.extractMin());
	            }
	            
	            int median = 0;
	            if(lineNo%2==0)	//Even
	            {
	            	median = lowHeap.peek();
	            }
	            else			//Odd
	            {
	            	if(lowHeap.size() > highHeap.size())
	            		median = lowHeap.peek();
	            	else
	            		median = highHeap.peek();
	            }
	            
	            medianSum += median;
	            line = br.readLine();
	            lineNo++; 
	        }
		}
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
		
		System.out.println(medianSum%10000);
	}
}

class MinHeap
{
	public int[] array;
	private int size;
	
	public MinHeap()
	{
		array = new int[5002];
	}
	
	public int size() {
		return size;
	}
	
	public int peek()
	{
		if(array.length>0)
			return array[0];
		else 
			return  Integer.MAX_VALUE;
	}
	
	public void push(int val)
	{
		array[size++] = val;
		int index = size-1;
		bubbleUp(index);
	}
	
	public int extractMin()
	{
		int returnVal = array[0];
		array[0] = array[size-1];
		array[size-1]=0;
		size--;
		int index = 0;
		bubbleDown(index);
		return returnVal;
	}


	private void bubbleUp(int index) 
	{
		if(index<1) return;
		int i = 0;
		if(index%2==0)
			i = index-1;
		else
			i = index;
		if(array[index]<array[i/2])
		{
			swap(index,i/2);
			bubbleUp(i/2);
		}
		else
			return;
	}
	
	private void swap(int index, int index2) {
		int temp = array[index];
		array[index] = array[index2];
		array[index2]= temp;
	}


	private void bubbleDown(int index) 
	{
		if(isCorrectHeap(index))
			return;
		
		int i = index+1;
		if(2*i <= size-1)
		{
		if(array[2*i-1]<=array[2*i])
		{
			swap(index,2*i-1);
			if(2*i-1<size);
				bubbleDown(2*i-1);
		}
		else
		{
			swap(index,2*i);
			if(2*i<size);
				bubbleDown(2*i);
		}
		}
		else if(2*i-1 < size-1 )
		{
			swap(index,2*i-1);
			if(2*i-1<size);
				bubbleDown(2*i-1);
		}
		return;
	}

	private boolean isCorrectHeap(int index) 
	{
		int i = index+1;
		if(2*i < size-1)
		{
			if(array[index] < array[2*i-1] && array[index] < array[2*i])
				return true;
		}
		else if(2*i-1 < size-1 && array[index] < array[2*i-1])
		{
			return true;
		}
		return false;
	}
}

class MaxHeap
{
	public MinHeap heap;
	public MaxHeap()
	{
		heap = new MinHeap();
	}
	
	public int size() 
	{
		return heap.size();
	}
	
	public int peek()
	{
		return heap.peek() * -1;
	}
	
	public void push(int val)
	{
		heap.push(val*-1);
	}
	
	public int extractMax()
	{
		return heap.extractMin() * -1;
	}

}

