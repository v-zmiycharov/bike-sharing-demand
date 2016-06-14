package Averages;

import java.util.HashMap;
import java.util.Map;
import Train.Trainer;
import weka.core.Instance;
import weka.core.Instances;

public class AveragesHelper {
	public static Map<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> averagesMap;
	public static Map<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> averagesMapCasual;
	public static Map<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> averagesMapRegistered;
	
	public static void setAveragesMap() throws Exception {
		averagesMap = setAveragesMap(Trainer.trainInstances);
		averagesMapCasual = setAveragesMap(Trainer.trainInstancesCasual);
		averagesMapRegistered = setAveragesMap(Trainer.trainInstancesRegistered);
	}

	private static HashMap<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> setAveragesMap(Instances instances) {
		HashMap<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> averagesMap = new HashMap<>();
		
		for(Instance instance : instances) {
			int year = Integer.parseInt(instance.toString(3));
			int month = Integer.parseInt(instance.toString(1));
			int hour = Integer.parseInt(instance.toString(4));
			boolean isWorkingDay = Integer.parseInt(instance.toString(7)) == 1;
			int currentResult = Integer.parseInt(instance.toString(26));
			
			if(!averagesMap.containsKey(year)) {
				averagesMap.put(year, new HashMap <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>());
			}
			if(!averagesMap.get(year).containsKey(month)) {
				averagesMap.get(year).put(month, new HashMap<Integer, Map<Boolean, AveragesDTO>>());
			}
			if(!averagesMap.get(year).get(month).containsKey(hour)) {
				averagesMap.get(year).get(month).put(hour, new HashMap<Boolean, AveragesDTO>());
			}
			if(!averagesMap.get(year).get(month).get(hour).containsKey(isWorkingDay)) {
				averagesMap.get(year).get(month).get(hour).put(isWorkingDay, new AveragesDTO());
			}
			
			AveragesDTO currentDTO = averagesMap.get(year).get(month).get(hour).get(isWorkingDay);
			
			currentDTO.count++;
			currentDTO.sum += currentResult;

			averagesMap.get(year).get(month).get(hour).put(isWorkingDay, currentDTO);
		}
		return averagesMap;
	}
	
	public static double getAverage(Instance instance, Map<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> map){
		int year = Integer.parseInt(instance.toString(3));
		int month = Integer.parseInt(instance.toString(1));
		int hour = Integer.parseInt(instance.toString(4));
		boolean isWorkingDay = Integer.parseInt(instance.toString(7)) == 1;
		
		return getAverage(year, month, hour, isWorkingDay, map);
	}
	
	private static double getAverage(int year, int month, int hour, boolean isWorkingDay,
			Map<Integer, Map<Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> map) {
		
		int result = map.get(year).get(month).get(hour).get(isWorkingDay).getAverage();
		
		if(month < 12 && map.get(year).containsKey(month+1)) {
			int nextMonthResult = map.get(year).get(month+1).get(hour).get(isWorkingDay).getAverage();
			
			result = (result + nextMonthResult)/2;
		}
		else if(map.containsKey(year+1)) {
			int nextYearResult = map.get(year+1).get(1).get(hour).get(isWorkingDay).getAverage();
			
			result = (result + nextYearResult)/2;
		}
		
		return result;
	}
}
