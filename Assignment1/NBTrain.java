
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NBTrain {

	public static void main(String[] args) throws IOException, NullPointerException {
		try
		{
			PrintWriter writer = new PrintWriter("NBTrainOutput.txt", "UTF-8");
			//System.out.println("Tadad!!!!");
			// TODO Auto-generated method stub
			//String megaDoc = new Scanner(new File("/home/mgadgil09/Desktop/Train_Data1")).useDelimiter("\\A").next();
			//String[] temp = megaDoc.split("\\n");
			//List<String>docs = Arrays.asList(temp);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
			//BufferedReader input = new BufferedReader(new FileReader(args[0]));
			String megaDoc;  
			// create a new Hashtable
		
			List<String>docs = new ArrayList<String>();
			int docCount = 0;
			//Map<String, String> countMap = new HashMap<String, String>();
			String count ="";
			long startTime = System.currentTimeMillis();
			while((megaDoc = input.readLine()) != null) {    
				
				megaDoc = megaDoc.trim();
				if (!megaDoc.equals("")) // don't write out blank lines
				{
					docs.add(megaDoc.trim().toString());
				}
			}
			//System.out.println(docs.size());
			/*
			 * Creating list containing label-word pairs
			 * 
			 */
			
			for(int i=0;i<docs.size();i++)
			{
				docCount = 1; 
				String[] tokens = docs.get(i).split("\\t");
				//System.out.println(tokens[0]+"-----"+tokens[1]);
				String [] wordArray = tokens[1].split("\\s+");
				
				for (int k = 0; k < wordArray.length; k++) {
					wordArray[k] = wordArray[k].replaceAll("\\W", "");
					wordArray[k] = wordArray[k].replaceAll("_", "");
					
				}
				Pattern p = Pattern.compile("[a-zA-Z](CAT)");
				Matcher labelMatch = p.matcher(tokens[0]);
				List<String> labelArray = new ArrayList<String>();
				while(labelMatch.find())
				{
					labelArray.add(labelMatch.group());

				}
				for(int x=0;x< labelArray.size();x++)
				{
					for(int y=0;y<wordArray.length;y++)
					{
						//pair=new HashMap<String,String>();
						if(wordArray[y].length()>0)
						System.out.println(labelArray.get(x)+"-->"+wordArray[y]);
						writer.println(labelArray.get(x)+"-->"+wordArray[y]);
						
						//pair.put(labelArray.get(x),wordArray[y]);
						//output.add(pair);
						
						
					}
					//will need a count of particular label occurring in how many documents.
					//which will further be needed to calculate P(c) = Nc/N
					//so this will later on give me Nc.
					count += "***"+Integer.toString(docCount)+","+labelArray.get(x).toString()+"\n";
					
					//countMap.put(Integer.toString(docCount),labelArray.get(x));
					
				}
			
			}
			

			System.out.println(count);
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			writer.println("Time in ms:  " + totalTime);
			writer.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
