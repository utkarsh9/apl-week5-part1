import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataStorageManager {

	public List<String> words = new ArrayList<String>();
	public Map<Method, Object> wordEvents = new HashMap<Method, Object>();
	public StopWordFilter swf;
	public WordFrequencyFramework wff;

	public DataStorageManager(WordFrequencyFramework wff, StopWordFilter swf) {
		this.wff = wff;
		this.swf = swf;
		init();
	}

	public void init() {
		try {
			wff.registerForLoadEvent(this.getClass().getMethod("load", String.class),this);
			wff.registerForDoWorkEvent(this.getClass().getMethod("produceWords", (Class<?>[])null),this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void load(String filePath) {
		try {
			words = Files.lines(Paths.get(filePath)).flatMap(line -> Arrays.stream(line.split("[\\s,;:?._!--]+")))
					.map(s -> s.toLowerCase()).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void produceWords() {
		try {
			for (int i = 0; i < words.size(); i++) {
				if (!this.swf.isStopWord(words.get(i)) && !"".equals(words.get(i)) && !"s".equals(words.get(i))) {
					for (Method method : wordEvents.keySet()) {
						method.invoke(wordEvents.get(method), words.get(i));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerEventForWord(Method method, WordFrequencyCounter wfc) {
		wordEvents.put(method, wfc);
	}

}