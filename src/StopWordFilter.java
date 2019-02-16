import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StopWordFilter {
	
	List<String> stopWords=new ArrayList<String>();
	WordFrequencyFramework wff;
	
	public StopWordFilter(WordFrequencyFramework wfapp) {
		this.wff=wfapp;
		init();
	}

	public void init(){
		try {
			wff.registerForLoadEvent(this.getClass().getMethod("load", String.class),this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void load(String filePath){
		try {
			stopWords = Files.lines(Paths.get(filePath)).map(line -> line.split(",")).flatMap(Arrays::stream)
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isStopWord(String word){
		if(this.stopWords.contains(word)){
			return true;
		}
		return false;
	}

}