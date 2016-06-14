package Averages;

import java.util.HashMap;
import java.util.Map;
import Train.Trainer;
import weka.core.Instance;

public class AveragesHelper {
	private static Map<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>> AveragesMap;
	
	public static void setAveragesMap() throws Exception {
		AveragesMap = new HashMap<Integer, Map <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>>();
		
		for(Instance instance : Trainer.TrainInstances) {
			int year = Integer.parseInt(instance.toString(3));
			int month = Integer.parseInt(instance.toString(1));
			int hour = Integer.parseInt(instance.toString(4));
			boolean isWorkingDay = Integer.parseInt(instance.toString(7)) == 1;
			int currentResult = Integer.parseInt(instance.toString(26));
			
			if(!AveragesMap.containsKey(year)) {
				AveragesMap.put(year, new HashMap <Integer, Map<Integer, Map<Boolean, AveragesDTO>>>());
			}
			if(!AveragesMap.get(year).containsKey(month)) {
				AveragesMap.get(year).put(month, new HashMap<Integer, Map<Boolean, AveragesDTO>>());
			}
			if(!AveragesMap.get(year).get(month).containsKey(hour)) {
				AveragesMap.get(year).get(month).put(hour, new HashMap<Boolean, AveragesDTO>());
			}
			if(!AveragesMap.get(year).get(month).get(hour).containsKey(isWorkingDay)) {
				AveragesMap.get(year).get(month).get(hour).put(isWorkingDay, new AveragesDTO());
			}
			
			AveragesDTO currentDTO = AveragesMap.get(year).get(month).get(hour).get(isWorkingDay);
			
			currentDTO.count++;
			currentDTO.sum += currentResult;

			AveragesMap.get(year).get(month).get(hour).put(isWorkingDay, currentDTO);
		}
	}
	
	public static double getAverage(Instance instance){
		int year = Integer.parseInt(instance.toString(3));
		int month = Integer.parseInt(instance.toString(1));
		int hour = Integer.parseInt(instance.toString(4));
		boolean isWorkingDay = Integer.parseInt(instance.toString(7)) == 1;
		
		return getAverage(year, month, hour, isWorkingDay);
	}
	
	private static double getAverage(int year, int month, int hour, boolean isWorkingDay) {
		int result = AveragesMap.get(year).get(month).get(hour).get(isWorkingDay).getAverage();
		
		if(month < 12 && AveragesMap.get(year).containsKey(month+1)) {
			int nextMonthResult = AveragesMap.get(year).get(month+1).get(hour).get(isWorkingDay).getAverage();
			
			result = (result + nextMonthResult)/2;
		}
		else if(AveragesMap.containsKey(year+1)) {
			int nextYearResult = AveragesMap.get(year+1).get(1).get(hour).get(isWorkingDay).getAverage();
			
			result = (result + nextYearResult)/2;
		}
		
		return result;
	}
}
