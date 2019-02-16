import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordFrequencyFramework {

	Map<Method,Object> loadEvents = new LinkedHashMap<Method,Object>();
	Map<Method,Object> doWorkEvents = new LinkedHashMap<Method,Object>();
	Map<Method,Object> endEvents = new LinkedHashMap<Method,Object>();

	public void registerForLoadEvent(Method method,Object object) {
		loadEvents.put(method,object);
	}

	public void registerForDoWorkEvent(Method method,Object object) {
		doWorkEvents.put(method,object);
	}

	public void registerForEndEvent(Method method,Object object) {
		endEvents.put(method,object);
	}

	public void run(String path) {
		try {
			for (int i = 0; i < loadEvents.size(); i++) {
				for(Method method:loadEvents.keySet()){
					if(loadEvents.get(method) instanceof DataStorageManager)
						method.invoke(loadEvents.get(method), path);
					else
						method.invoke(loadEvents.get(method), "stop-words.txt");
				}
			}
			for (Method method: doWorkEvents.keySet()) {
				method.invoke(doWorkEvents.get(method),(Object[])null);
			}
			for (Method method : endEvents.keySet()) {
				method.invoke(endEvents.get(method), (Object[])null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
