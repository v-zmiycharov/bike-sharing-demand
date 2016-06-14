package Train;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

import Averages.AveragesHelper;
import Utils.Constants;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.M5P;
import weka.core.Instance;
import weka.core.Instances;

public class Trainer {
	public static Instances trainInstances;
	public static Instances trainInstancesCasual;
	public static Instances trainInstancesRegistered;
	public static Classifier classifier;
	public static Classifier classifierCasual;
	public static Classifier classifierRegistered;
	public static Instances testInstances;
	
	public static void setTrainInstances() throws Exception {
		trainInstances = new Instances(
				new BufferedReader(new FileReader(Constants.TRAIN_ARFF_PATH)));
		trainInstances.setClassIndex(trainInstances.numAttributes() - 1);

		trainInstancesCasual = new Instances(
				new BufferedReader(new FileReader(Constants.TRAIN_ARFF_PATH_CASUAL)));
		trainInstancesCasual.setClassIndex(trainInstancesCasual.numAttributes() - 1);

		trainInstancesRegistered = new Instances(
				new BufferedReader(new FileReader(Constants.TRAIN_ARFF_PATH_REGISTERED)));
		trainInstancesRegistered.setClassIndex(trainInstancesRegistered.numAttributes() - 1);
	}
	
	public static void generateClassifier() throws Exception {
		classifier = generateClassifier(Constants.CLASSIFIER_PATH, trainInstances);
		classifierCasual = generateClassifier(Constants.CLASSIFIER_PATH_CASUAL, trainInstancesCasual);
		classifierRegistered = generateClassifier(Constants.CLASSIFIER_PATH_REGISTERED, trainInstancesRegistered);
	}

	private static Classifier generateClassifier(String path, Instances instances)
			throws Exception, IOException, FileNotFoundException, ClassNotFoundException {
		File classifierFile = new File(path);
		
		Classifier classifier = null;
		if(!classifierFile.exists()) {
			classifier = new M5P();
			classifier.buildClassifier(instances);
			
			classifierFile.createNewFile();
			weka.core.SerializationHelper.write(path, classifier);
		}
		else {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			classifier = (Classifier) ois.readObject();
			ois.close();
		}
		return classifier;
	}

	public static void setTestInstances() throws Exception {
		testInstances = new Instances(
				new BufferedReader(new FileReader(Constants.TEST_ARFF_PATH)));
		testInstances.setClassIndex(testInstances.numAttributes() - 1);
	}

	public static void classifyTestInstances() throws Exception {
		for (int i = 0; i < testInstances.numInstances(); i++) {
			int count = (int) classifier.classifyInstance(testInstances.instance(i));
			if (count < 0) {
				count = 0;
			}
//			double averageForMonth = AveragesHelper.getAverage(testInstances.instance(i), AveragesHelper.averagesMap);
//			int result = (int) (0.6 * count + 0.4 * averageForMonth);
//			if (result >= 2) {
//				result -= 2;
//			}

			int countCasual = (int) classifierCasual.classifyInstance(testInstances.instance(i));
			if (countCasual < 0) {
				countCasual = 0;
			}
//			averageForMonth = AveragesHelper.getAverage(testInstances.instance(i), AveragesHelper.averagesMapCasual);
//			int resultCas = (int) (0.6 * countCasual + 0.4 * averageForMonth);
//			if (resultCas >= 2) {
//				resultCas -= 2;
//			}
		   
			int countRegsitered = (int) classifierRegistered.classifyInstance(testInstances.instance(i));
			if (countRegsitered < 0) {
				countRegsitered = 0;
			}
			double averageForMonth = AveragesHelper.getAverage(testInstances.instance(i), AveragesHelper.averagesMapRegistered);
			int resultReg = (int) (0.6 * countRegsitered + 0.4 * averageForMonth);
			if (resultReg >= 2) {
				resultReg -= 2;
			}

			testInstances.instance(i).setClassValue((count + countCasual + resultReg) / 2);

		}
	}
	
	public static void writeSubmissionResults() throws Exception {
		File outputFile = new File(Constants.SUBMISSION_CSV_PATH);
		
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, false));
		
		bw.append("datetime,count");
		
		for (int i = 0; i < testInstances.numInstances(); i++) {
			Instance instance = testInstances.instance(i);
			
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
