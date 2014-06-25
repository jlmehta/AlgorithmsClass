package classProblems;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem: This file contains all of the 100,000 integers between 1 and 100,000 (inclusive) in some order, with no integer repeated.
 * Your task is to compute the number of inversions in the file given, where the ith row of the file indicates the ith entry of an array.
 * Because of the large size of this array, you should implement the fast divide-and-conquer algorithm covered in the video lectures. 
 * The numeric answer for the given input file should be typed in the space below.
 * 
 * Approach: Do a simple merge sort of the of the numbers in the file and count the number of inversions as they occur in the merge method
 * 
 * @author JLMehta
 */
public class MergeSort {
	static double inversions = 0;
	
	public static void main(String args[])
	{
		int[] input = new int[100000];//{3,4,6,1,2,5}; 
		int i =0;
		Scanner fis=null;
		try 
		{
			String path = new File("").getAbsolutePath(); 
			fis = new Scanner(new FileInputStream(path+ "/bin/inputFiles/IntegerArray.txt"));
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		while (fis.hasNextLine()){
	        input[i] = Integer.parseInt(fis.nextLine());
	        i++;
	      }
		System.out.println("\nUnsorted");
		for(i=0; i<input.length; i++)
			System.out.print(input[i]+" ");
		
		int[] output = mergeSort(input);
		
		System.out.println("\nMerge Sorted");
		for(i=0; i<input.length; i++)
			System.out.print(output[i]+" ");
		
		System.out.println("\nInversions: "+inversions);
		
		//Test
		/*String v = "\nWorks!!";
		for(int i=0; i<input.length; i++)
			if(i>0 && output[i]-output[i-1]<0)
			{
				v = "\nDoesn't Work!!" ;
				break;
			}
		System.out.print(v);*/
	}
	
	private static int[] mergeSort(int[] input)
	{
		if(input.length<2) return input;
		int[] a = Arrays.copyOfRange(input, 0, (input.length/2));
		int[] b = Arrays.copyOfRange(input, (input.length/2), input.length);
		
		int[] output = merge(a, b);
		return output;
	}
	
	private static int[] merge(int[] l, int[] r)
	{
		if(l.length>1)
			l = mergeSort(l);
		if(r.length>1)
			r = mergeSort(r);
		int i=0,j=0;
		int[] res = new int[l.length+r.length];
		for(int k=0; k < (l.length+r.length); k++)
		{
			if(i>=l.length)
			{
				res[k] = r[j];
				j++; continue;
			}
			if(j>=r.length)
			{
				res[k] = l[i];
				i++; continue;
			}
			if(l[i] < r[j])
			{
				res[k] = l[i];
				i++;
			}
			else if(l[i]>r[j])
			{
				res[k] = r[j];
				j++;
				inversions += (l.length - i);
			}
			else
			{
				res[k] = l[i];
				i++;
				k++;
				res[k] = r[j];
				j++;
			}
		}
		return res;
	}
}
