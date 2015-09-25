
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MergeCounts {

	public static void main(String[] args) throws IOException, NullPointerException {
		// TODO Auto-generated method stub
		try
		{

			//String sortedRes = new Scanner(new File("/home/mgadgil09/Documents/workspace/proj1NB/src/newFile1.txt")).useDelimiter("\\A").next();
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String sortedResult="";
			String sortedRes = "";
			
			//List<String> temp = new ArrayList<String>();
			List<String> classCountArray = new ArrayList<String>();
			while ((sortedResult = input.readLine()) != null)
				   
			      sortedRes += sortedResult + "\n";
			
			
			String[] countArray = sortedRes.toString().split("\\s+");
			Map<String,Integer> countMap = new HashMap<String,Integer>();
			int count = 0;
			for(Integer i=0,j=1;i<countArray.length && j<countArray.length;j++)
			{
				//System.out.println("going in");
				if(countArray[i].contains("***")){
					i++;
					//j++;
					//break;
					//continue;
				}
				else{
					if(countArray[i].equals(countArray[j])){
						count+=1;
					}
					else {

						if(countArray[i].length()>0)
							countMap.put(countArray[i],count+1);	//count will also include ith element
						count++;
						//System.out.println(countArray[i]+"	"+count);
						i=j;
						count=0;
					}
				}
			}


			//System.out.println("Vaat lagliye");
			/*
			 * print the map
			 */

			Set<String> setOfKeys = countMap.keySet();

			Iterator<String> iterator = setOfKeys.iterator();
			while (iterator.hasNext()) 
			{
				//System.out.println("aat jaata ahe ka?");
				String key = (String) iterator.next();
				int value = countMap.get(key);

				System.out.println(key+"__"+ value);
			}
			Pattern p = Pattern.compile("\\*+[0-9]\\,[A-Za-z]*");
			
			Matcher labelMatch = p.matcher(sortedRes);
			while(labelMatch.find())
			{

				System.out.println(labelMatch.group().toString());
			}
		
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
