package classProblems;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * The file contains all of the integers between 1 and 10,000 (inclusive, with no repeats) in unsorted order. 
 * The integer in the ith row of the file gives you the ith entry of an input array.
 * 
 * Your task is to compute the total number of comparisons used to sort the given input file by QuickSort. 
 * As you know, the number of comparisons depends on which elements are chosen as pivots, so we'll ask you to explore 
 * three different pivoting rules.
 * You should not count comparisons one-by-one. Rather, when there is a recursive call on a subarray of length m, 
 * you should simply add m−1 to your running total of comparisons. (This is because the pivot element is compared to 
 * each of the other m−1 elements in the subarray in this recursive call.) 
 * 
 * Part 1: For the first part of the programming assignment, you should always use the first element of the 
 * array as the pivot element.
 * Part 2: Compute the number of comparisons (as in Problem 1), always using the final element of the given
 * array as the pivot element.
 * Part 3: Compute the number of comparisons (as in Problem 1), using the "median-of-three" pivot rule. 
 * [The primary motivation behind this rule is to do a little bit of extra work to get much better performance on 
 * input arrays that are nearly sorted or reverse sorted.] In more detail, you should choose the pivot as follows. 
 * Consider the first, middle, and final elements of the given array. (If the array has odd length it should be clear 
 * what the "middle" element is; for an array with even length 2k, use the kth element as the "middle" element. So for 
 * the array 4 5 6 7, the "middle" element is the second one ---- 5 and not 6!) Identify which of these three elements 
 * is the median (i.e., the one whose value is in between the other two), and use this as your pivot.
 * 
 * @author JLMehta
 */

public class QuickSort {
	static int input[] = new int[10000];
	static int comp = 0;
	//static int input[] = {3,4,6,1,2,5};
	//static int input[] = {3,8,2,5,1,4,7,6};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		fill();
		
		partition(0,input.length-1);
		
		System.out.println("\nQuick Sorted");
		for(i=1; i<input.length; i++)
		{
			if(input[i]-input[i-1]!=1)	
			System.out.print(input[i]+" ");
		}
		System.out.println(comp);
	}
	
	public static void partition(int l, int r)
	{	
		if(l>=r) return;
		comp += r-l;
		
		int p = 0;
		//p = getLeftPivot(l, r);  // Part 1 : First Element
		//p = getRightPivot(l, r);	// Part 2 : Final element
		p = getMedianPivot(l, r);	// Part 3 : Median-of-three rule
		
		int i=l+1;
		int j=l+1;
		int temp = 0;
		while(j<=r)
		{
			if(input[j]<p)
			{
				temp = input[j];
				input[j] = input[i];
				input[i]=temp;
				i++;
			}
			else if(input[j]>p)
			{
			}
			j++;
		}
		
		temp = input[l];
		input[l] = input[i-1];
		input[i-1] = temp;
		
		partition(l,i-2);
		partition(i,r);
	}
	
	
	public static int getLeftPivot(int l, int r)
	{
		return input[l];
	}
	
	public static int getRightPivot(int l, int r)
	{
		int temp = input[r];
		input[r] = input[l];
		input[l] = temp;
		
		return temp;
	}
	
	public static int getMedianPivot(int l, int r)
	{
		int length = l +  r + 1;
		int m = 0;
		if(length%2==0)
		{
			m = (length/2)-1;
		}
		else
		{
			m = (length/2);
		}
		
		int med = getMedian(l,m, r);
		
		int temp = input[med];
		input[med] = input[l];
		input[l] = temp;
		
		return temp;
		
	}
	
	private static int getMedian(int l, int m, int r)
	{
		if (input[l] > input[m]) {
			  if (input[m] > input[r]) {
			    return m;
			  } else if (input[l] > input[r]) {
			    return r;
			  } else {
			    return l;
			  }
			} else {
			  if (input[l] > input[r]) {
			    return l;
			  } else if (input[m] > input[r]) {
			    return r;
			  } else {
			    return m;
			  }
			}
	}
	
	private static void fill()
	{
		int i =0;
		Scanner fis=null;
		try 
		{
			String path = new File("").getAbsolutePath();  
			fis = new Scanner(new FileInputStream(path+ "/bin/inputFiles/QuickSortData.txt"));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		while (fis.hasNextLine()){
	        input[i] = Integer.parseInt(fis.nextLine());
	        i++;
	    }
	}

}
