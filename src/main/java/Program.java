import Averages.AveragesHelper;
import Train.Trainer;
import fixer.Fixer;

public class Program {

	public static void main(String[] args) throws Exception {
		System.out.println("Set train instances");
		Trainer.setTrainInstances();

		System.out.println("Fix broken training data");
		Fixer.fixBrokenData(true);

		System.out.println("Calculate averages");
		AveragesHelper.setAveragesMap();

		System.out.println("Generate classifier");
		Trainer.generateClassifier();

		System.out.println("Set test instances");
		Trainer.setTestInstances();

		System.out.println("Fix broken testing data");
		Fixer.fixBrokenData(false);

		System.out.println("Classify test instances");
		Trainer.classifyTestInstances();

		System.out.println("Write submission");
		Trainer.writeSubmissionResults();
	}

}
