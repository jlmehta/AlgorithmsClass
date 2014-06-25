package classProblems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Problem: The goal of this problem is to implement a variant of the 2-SUM algorithm (covered in the Week 6 lecture on hash 
 * table applications).
 * 
 * The file contains 500,000 positive integers (there might be some repetitions!).This is your array of integers, with the 
 * ith row of the file specifying the ith entry of the array.
 * 
 * Your task is to compute the number of target values t in the interval [2500,4000] (inclusive) such that there are distinct 
 * numbers x,y in the input file that satisfy x+y=t. (NOTE: ensuring distinctness requires a one-line addition to the algorithm 
 * from lecture.)
 * 
 * @author JLMehta
 */
public class TwoSum {

	/**
	 * To implement the two sum algorithm with a HashMap the integer set in the input file G:\AlgoClass\PrgAssn6\HashInt.txt
	 */
	
	static String inputFile = new File("").getAbsolutePath() + "/bin/inputFiles/HashInt.txt"; 
	public static void main(String[] args) {
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		HashSet<Integer> count = new HashSet<Integer>();
		//int count= 0;
		try {
	        BufferedReader br = new BufferedReader(new FileReader(inputFile));
	        String line = br.readLine();
	        while (line != null) 
	        {
	            int i = Integer.parseInt(line);
	            if(map.containsKey(i))
	            {
	            	map.put(i, (map.get(i).intValue()+1));
	            }
	            else
	            {
	            	map.put(i, 1);
	            }
	            numbers.add(i);
	            line = br.readLine();
	        }
		}
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
		
		for(int i = 0; i<numbers.size(); i++) 
		{
			int num = numbers.get(i);
			if(num < 4001)
			{
				for(int t=2500; t<4001; t++)
				{
					if(t-num != num && map.containsKey(t-num))
					{
						count.add(t);
					}
				}
			}	
		}
		System.out.println(count.size());

	}

}
