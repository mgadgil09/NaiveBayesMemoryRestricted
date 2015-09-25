
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NBTest1 {

	static List<String> labels = new ArrayList<String>();

	static List<String> testDocumentList = new ArrayList<>();

	//static int totalClassOccCount=0;
	static List<String> classCountArray = new ArrayList<String>();
	static List<String> vocabulary = new ArrayList<String>();
	static final List<Map<String, String>> tokenMapList = new ArrayList<Map<String, String>>();
	static int vocabCount=0;
	static Map<String, String> tokenMap = new HashMap<String, String>();
	static Map<String,Double> condProbabilities = new HashMap<String,Double>();
	public static void main(String args[]) throws FileNotFoundException{
		try{
			formatTestData(args[0]);
			int classOccurCount=0;
			Map<String,Integer> classCountMap = new HashMap<String,Integer>();
			BufferedReader input = new BufferedReader(new FileReader("C:/Users/mgadgil09/workspace/mmd1/src/mergedOutput.txt"));
			//BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			//Scanner input = new Scanner(new InputStreamReader(System.in, Charset.defaultCharset()));
			String mergedResult="";
			String[] vocabWords;
			String[] getLabel = null;


			Pattern p = Pattern.compile("\\*+[0-9]\\,[A-Za-z]*");
			while ((mergedResult = input.readLine()) != null)
			{	   
				//mergedRes += mergedResult + "\n";
				//String[] mergedCountArray = mergedRes.toString().split("\\n");
				if(mergedResult.contains("***")){
					Matcher labelMatch = p.matcher(mergedResult);
					while(labelMatch.find())
					{

						classCountArray.add(labelMatch.group().toString());			

					}
				}
				else{					
					if(mergedResult.length()>0 && mergedResult!=null){
						//getLabel = mergedResult.split("-->");
						labels.add(mergedResult.split("-->")[0]);
						//vocabWords = getLabel[1].split("__");
						if(!vocabulary.contains(mergedResult.split("-->")[1].split("__")[0]))
							vocabulary.add(mergedResult.split("-->")[1].split("__")[0]);
						//tokenList.add(mergedResult);
						tokenMap = new HashMap<String,String>();
						tokenMap.put(mergedResult.split("-->")[0],mergedResult.split("-->")[1]);
						tokenMapList.add(tokenMap);
					}
				}

			}
			vocabCount = vocabulary.size();
			for(Integer i=0,j=1;i<labels.size() && j<labels.size();j++)
			{
				//System.out.println("going in");
				if(labels.get(i).equals(labels.get(j))){
					classOccurCount+=1;
				}
				else {

					classCountMap.put(labels.get(i),classOccurCount+1);	//count will also include ith element
					classOccurCount++;

					i=j;
					classOccurCount=0;
				}
			}
			classCountMap.put(labels.get((labels.size()-1)),classOccurCount+1);
			//List<String> s = Classes();
			calculateProbTest();



		}catch(Exception e){
			e.printStackTrace();
		}

	}


	/*
	 * format test input data
	 * 
	 */
	public static void formatTestData(String args){
		try{
			String fileName = "";
			String testData = "";




			BufferedReader br;
			if (args.length() > 0) { 
				fileName = args;
			}
			br = new BufferedReader(new FileReader(fileName));
			while((testData = br.readLine()) !=null){

				if(testData.length()>0)
					testDocumentList.add(testData);

			}

		}catch(Exception e){

		}
	}
	/*
	 * 
	 * Extract words for every test document and put it in the map words
	 */
	public static Map<String,Integer> getWords(int i){
		Map<String,Integer> words = new HashMap<String,Integer>();
		String[] tempWordArray;
		int count =0;
		tempWordArray = testDocumentList.get(i).split("\\s+");
		for (int k = 0; k < tempWordArray.length; k++) {
			//tempWordArray[k] = tempWordArray[k].replaceAll("[\\s\\-()]", "");
			tempWordArray[k] = tempWordArray[k].replaceAll("\\W", "");
		}
		for(int w=1;w<tempWordArray.length;w++){
			count=0;
			if(!words.containsKey(tempWordArray[w])){
				//count++;
				if(tempWordArray[w].length() > 0) 
					words.put(tempWordArray[w],1);

			}
			else if(words.containsKey(tempWordArray[w])){
				if(words.get(tempWordArray[w])!=null)
					count = words.get(tempWordArray[w]);
				count++;
				if(tempWordArray[w].length() > 0) 
					words.put(tempWordArray[w],count);
				//System.out.println("Count of "+ words.get(tempWordArray[w].toString() + "	count is"+ words.get(tempWordArray[w])));
			}
		}
		return words;

		//System.out.println("count for discharging---"+words.get("hello"));
		//}

	}
	/*
	 * print the map
	 */
	public static void printMap(Map<String,Integer> m){
		Set<String> setOfKeys = m.keySet();

		Iterator<String> iterator = setOfKeys.iterator();
		while (iterator.hasNext()) 
		{

			String key = (String) iterator.next();
			int value = m.get(key);

			System.out.println(key+"__"+ value);
		}
	}

	/*
	 * Calculating count(w,c)
	 * 
	 */
	public static int wordPerClassProb(String className, String word){
		/*
		 * count(w,c)
		 *
		 *
		 */
		//tokenList --> label-->word_count

		//		for(int i=0;i<tokenList.size();i++){
		//			if()
		//		}

		for(Map<String,String> m : tokenMapList)
		{
			if(m.get(className)!=null && m.get(className).toString().contains(word)){
				String[] value = m.get(className).toString().split("__");
				return Integer.parseInt(value[1]);
			}

		}


		return 0;

	}
	
	/*
	 * 
	 * count of all words in a particular class
	 */
	public static int countOfWordsInClass(String className){
		int val = 0;
		Map<String,Integer> wordMap = new HashMap<String,Integer>();
		for(Map<String,String> m : tokenMapList)
		{

			String key = className;
			//System.out.println("key-----"+key);
			String value = (String)m.get(key);
			//System.out.println("value---"+value);
			if(m.containsValue(value)){
				String[] countOfWords = value.split("__");
				if(!wordMap.containsKey(key))
					wordMap.put(key, Integer.parseInt(countOfWords[1].trim()));
				else{
					val = wordMap.get(key);
					val += Integer.parseInt(countOfWords[1].trim());
					wordMap.put(key, val);
				}
			}

		}
		return wordMap.get(className);
	}

	/*
	 * GET list of all class names
	 * 
	 */
	public static List<String> Classes(){
		List<String> classList = new ArrayList<String>();
		for(int n=0;n<labels.size();n++){
			if(!classList.contains(labels.get(n))){
				classList.add(labels.get(n));
			}
		}
		//System.out.println(classList);
		return classList;

	}

	/*
	 * calculating Nc
	 * 
	 */
	public static Map<String,Integer> classPerDocCount(List<String> ls){
		List<String> refinedLs = new ArrayList<String>();
		Map<String,Integer> classDocCountMap = new HashMap<String,Integer>();
		for(int i = 0; i < ls.size(); i++)
		{
			if(ls.get(i).contains("***"))
			{
				refinedLs.add(ls.get(i).replace("***", ""));

			} 

		}
		//System.out.println(refinedLs);   // New List
		int count=0;

		for(int i=0;i<refinedLs.size();i++){
			String[] arr1 = refinedLs.get(i).split(",");
			//			count = Integer.parseInt(arr1[0]);

			if(!classDocCountMap.containsKey(arr1[1])){
				classDocCountMap.put(arr1[1],Integer.parseInt(arr1[0]));
			}
			else{
				int val = classDocCountMap.get(arr1[1]).intValue();
				val = val + Integer.parseInt(arr1[0]);
				classDocCountMap.put(arr1[1],val);
			}
		}
		return classDocCountMap;

	}	

	/*
	 * calculate class in all documents N
	 * 
	 * 
	 */
	public static int classInAllDocCount(){
		Map<String,Integer> countMap = classPerDocCount(classCountArray);
		Set<String> setOfKeys = countMap.keySet();
		int value=0;
		Iterator<String> iterator = setOfKeys.iterator();
		while (iterator.hasNext()) 
		{
			//System.out.println("aat jaata ahe ka?");
			String key = (String) iterator.next();
			value += countMap.get(key);

			//System.out.println("__"+ value);
		}
		//System.out.println(value);
		return value;
	}
	/*
	 * Probability of class P(c) = Nc/N
	 * 
	 */
	public static double probOfClass(String className){
		Map<String,Integer> map= classPerDocCount(classCountArray);
		int nc = map.get(className);
		int n = classInAllDocCount();
		List<String> classList = Classes();
		int domC = classList.size();
		double probClass = (double)(nc)/ (double)n;
		//System.out.println("This is p(c):  "+probClass);
		return probClass;
	}
	/*
	 * 
	 * get probability for the given test word P(w|c)
	 */
	public static double getTestWordProb(String word, String className){

		if(condProbabilities.containsKey(word+"_"+className)){
			return condProbabilities.get(word+"_"+className);
		}
		else{
			//double val = (double)(0 + 1*(double)(1/vocabCount))/(countOfWordsInClass(className) + 1); //dirichlet
			//System.out.println("this is for unknown word:  "+val);
			return (double)(0 + 1)/(double)(countOfWordsInClass(className)+ vocabCount);
		}
	}


	/*
	 * we have to calculate Prob(w|c)
	 * 
	 */
	public static void condProb(String word){
		List<String> classList = Classes();
		double pOfWC;
		for(String c : classList){
			int countWC = wordPerClassProb(c,word);
			int allWordsInClass = countOfWordsInClass(c);

			pOfWC = (double)(countWC + 1)/(double)(allWordsInClass + vocabCount); //Dirichlet
			//System.out.println("this is condProb:"+pOfWC);
			condProbabilities.put(word+"_"+c, pOfWC);

		}



	}

	/*
	 * Calculate probability for c|w - P(c|w)
	 * 
	 */
	public static void calculateProbTest(){
		double pOfWc = 0;
		double pOfCd = 0;
		double sum = 0;
		int winner=0;
		List<String> labelArray = new ArrayList<String>();
		Map<String,Double> probMap = new HashMap<String,Double>();
		for(int i=0;i<testDocumentList.size();i++){
			condProbabilities.clear();
			long startTime = System.currentTimeMillis();
			Pattern p = Pattern.compile("[a-zA-Z](CAT)");
			Matcher labelMatch = p.matcher(testDocumentList.get(i));
			labelArray.clear();
			while(labelMatch.find())
			{
				labelArray.add(labelMatch.group());

			}
			//get the words for every document
			System.out.println("--------Document "+i+"-------------");
			Map<String,Integer> words = getWords(i);

			for(String c : Classes())
			{

				sum=0;
				Set<String> setOfKeys = words.keySet();

				Iterator<String> iterator = setOfKeys.iterator();
				while (iterator.hasNext()) 
				{

					String key = (String) iterator.next();
					condProb(key);
					int wordFrequency = words.get(key);
					double wordProb = getTestWordProb(key,c);
					//System.out.println("p(w|c)"+wordProb+"	"+wordFrequency);
					//System.out.println("log of p(w|c)  "  +Math.log(wordProb));
					pOfWc = (double)wordFrequency * Math.log(wordProb);
					sum += pOfWc;

				}

				double pOfC = Math.log(probOfClass(c));
				//System.out.println("log of p(c)  "+pOfC);
				pOfCd = sum + pOfC;
				System.out.println(c+"	"+pOfCd);
				probMap.put(c, pOfCd);

			}

			Map.Entry<String,Double> maxEntry = null;

			for (Map.Entry<String,Double> entry : probMap.entrySet())
			{
				if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
				{
					maxEntry = entry;
				}
			}
			if(labelArray.contains(maxEntry.getKey()))
				winner++;
			long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println(totalTime+"ms  for document  "+ i);

		}
		double accuracy = (double)100/(double)(testDocumentList.size()*winner);
		System.out.println("Accuracy is  "+accuracy+"%");

	}

}





