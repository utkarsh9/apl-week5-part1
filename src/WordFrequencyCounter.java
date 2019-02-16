import java.util.HashMap;
import java.util.Map;

public class WordFrequencyCounter {
	
	Map<String,Integer> wordFrequencies=new HashMap<String,Integer>();
	WordFrequencyFramework wff;
	DataStorageManager dsm;
	
	public WordFrequencyCounter(WordFrequencyFramework wfapp, DataStorageManager dsm) {
		this.wff=wfapp;
		this.dsm=dsm;
		init();
	}
	
	public void init(){
		try {
			wff.registerForEndEvent(this.getClass().getMethod("printFrequencies", (Class<?>[])null),this);
			dsm.registerEventForWord(this.getClass().getMethod("incrementCount", String.class),this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void incrementCount(String word){
		int count=1;
		if(wordFrequencies.containsKey(word)){
			count=wordFrequencies.get(word);
			count+=1;
		}
		wordFrequencies.put(word, count);
	}
	
	public void printFrequencies(){
		wordFrequencies.entrySet().stream().sorted((element1, element2) -> Integer.compare(element2.getValue(), element1.getValue())).limit(25)
		 .forEach(element -> System.out.println(element.getKey() + "-" + element.getValue()));
	}

}
