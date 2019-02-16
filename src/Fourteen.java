import java.util.stream.Collectors;

public class Fourteen {
    
    public static void main(String[] args) {
		WordFrequencyFramework wfapp = new WordFrequencyFramework();
		StopWordFilter swf = new StopWordFilter(wfapp);
		DataStorageManager dsm = new DataStorageManager(wfapp, swf);
		WordFrequencyCounter wfc = new WordFrequencyCounter(wfapp, dsm);
		wfapp.run(args[0]);
		System.out.println("Number of Z words are " + dsm.words.parallelStream().filter(d -> swf.stopWords.parallelStream()
										.allMatch(f -> !d.equals(f) && !d.equals("") && !d.equals("s")
												&& d.contains("z"))).collect(Collectors.toMap(w -> w, w-> 1, Integer::sum)).size());
	}
}
