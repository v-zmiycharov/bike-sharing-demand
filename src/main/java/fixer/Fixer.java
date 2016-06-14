package fixer;

import Train.Trainer;
import weka.core.Instance;
import weka.core.Instances;

public class Fixer {
	private static final float ZERO = 0.005f;
	private static final int WIND_NUM = 12;
	private static final int WIND_NUM_PREV = 25;

	public static void fixBrokenData(boolean trainingData) {
		Instances instances = trainingData ? Trainer.TrainInstances : Trainer.TestInstances;

		fixInstance(instances, WIND_NUM);

		fixInstance(instances, WIND_NUM_PREV);
	}

	private static void fixInstance(Instances instances, int attributeNumber) {
		int numInstances = instances.numInstances();

		for (int i = 0; i < numInstances; ++i) {
			Instance currentInstance = instances.instance(i);

			String windSpeedString = currentInstance.toString(attributeNumber);
			if (!windSpeedString.contains("?")) {
				float windSpeed = Float.parseFloat(windSpeedString);
				if (windSpeed <= ZERO) {
					float prevValue = 0;
					if (i > 0) {
						String prevValueString = instances.instance(i - 1).toString(attributeNumber);
						if (!prevValueString.contains("?")) {
							prevValue = Float.parseFloat(prevValueString);
						}
					}

					int nextIndex = i + 1;
					while (nextIndex < numInstances
							&& !instances.instance(nextIndex).toString(attributeNumber).contains("?")
							&& Float.parseFloat(instances.instance(nextIndex).toString(attributeNumber)) <= ZERO) {
						++nextIndex;
					}

					float nextValue = 0;
					if (nextIndex < numInstances) {
						String nextValueString = instances.instance(nextIndex).toString(attributeNumber);
						if (!nextValueString.contains("?")) {
							nextValue = Float.parseFloat(nextValueString);
						}
					}

					float newValue = prevValue - (prevValue - nextValue) / (nextIndex - i + 1);
					currentInstance.setValue(attributeNumber, newValue);
				}
			}
		}
	}
}
