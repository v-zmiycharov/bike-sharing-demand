package Train;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;

import Averages.AveragesHelper;
import Utils.Constants;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instance;
import weka.core.Instances;

public class Trainer {
	public static Instances TrainInstances;
	public static Classifier Classifier;
	public static Instances TestInstances;
	
	public static void setTrainInstances() throws Exception {
		TrainInstances = new Instances(
				new BufferedReader(new FileReader(Constants.TRAIN_ARFF_PATH)));
		TrainInstances.setClassIndex(TrainInstances.numAttributes() - 1);
	}
	
	public static void generateClassifier() throws Exception {
		File classifierFile = new File(Constants.CLASSIFIER_PATH);
		
		if(!classifierFile.exists()) {
			Classifier = new MultilayerPerceptron();
			Classifier.buildClassifier(TrainInstances);
			
			classifierFile.createNewFile();
			weka.core.SerializationHelper.write(Constants.CLASSIFIER_PATH, Classifier);
		}
		else {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Constants.CLASSIFIER_PATH));
			Classifier = (Classifier) ois.readObject();
			ois.close();
		}
	}

	public static void setTestInstances() throws Exception {
		TestInstances = new Instances(
				new BufferedReader(new FileReader(Constants.TEST_ARFF_PATH)));
		TestInstances.setClassIndex(TestInstances.numAttributes() - 1);
	}

	public static void classifyTestInstances() throws Exception {
		for (int i = 0; i < TestInstances.numInstances(); i++) {
		   int count = (int)Classifier.classifyInstance(TestInstances.instance(i));
		   if(count < 0) {
			   count = 0;
		   }
		   
		   double averageForMonth = AveragesHelper.getAverage(TestInstances.instance(i));
		   
		   int result = (int)(0.6 * count + 0.4 * averageForMonth);
		   
		   if(result >= 2) {
			   result -= 2;
		   }
		   
		   TestInstances.instance(i).setClassValue(result);
		}
	}
	
	public static void writeSubmissionResults() throws Exception {
		File outputFile = new File(Constants.SUBMISSION_CSV_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("datetime,count");
		
		for (int i = 0; i < TestInstances.numInstances(); i++) {
			Instance instance = TestInstances.instance(i);
			
			bw.newLine();
			bw.append(getSubmissionString(instance));
		}
		
		bw.close();
	}
	
	private static String getSubmissionString(Instance instance) {
		return instance.toString(3) // year
				+ "-" + String.format("%02d", Integer.parseInt(instance.toString(1))) // month
				+ "-" + String.format("%02d", Integer.parseInt(instance.toString(2))) // day
				+ " " + String.format("%02d", Integer.parseInt(instance.toString(4))) // hour
				+ ":00:00"
				+ "," + instance.toString(instance.numAttributes() - 1);
	}
}
